package org.example.service;


import org.example.entity.Car;
import org.example.entity.Client;
import org.example.entity.Review;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;


import java.util.List;

public class ReviewService {

    public void addReview(Client client, Car car, String text, int rating) {
        Review review = new Review();
        review.setClient(client);
        review.setCar(car);
        review.setText(text);
        review.setRating(rating);

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(review);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<Review> searchReviews(String keyword) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            SearchSession searchSession = Search.session(session);
            return searchSession.search(Review.class)
                    .where(f -> f.match()
                            .fields("text")
                            .matching(keyword))
                    .fetchAllHits();
        }
    }

}
