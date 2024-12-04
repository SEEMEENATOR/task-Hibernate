package org.example.service;

import jakarta.persistence.EntityGraph;
import org.example.entity.Car;
import org.example.entity.CarShowroom;
import org.example.entity.Category;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class CarService {

    public void addCar(Car car) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(car);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
    public void addCategory(Category category) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(category);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void addCarShowroom(CarShowroom carShowroom) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(carShowroom);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<Car> findCarsByFilters(String brand, String categoryName, int year, double minPrice, double maxPrice) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Car c " +
                    "JOIN FETCH c.category " +
                    "WHERE c.brand LIKE :brand " +
                    "AND c.category.name LIKE :categoryName " +
                    "AND c.manufactureYear >= :year " +
                    "AND c.price BETWEEN :minPrice AND :maxPrice";

            Query<Car> query = session.createQuery(hql, Car.class);
            query.setParameter("brand", "%" + brand + "%");
            query.setParameter("categoryName", "%" + categoryName + "%");
            query.setParameter("year", year);
            query.setParameter("minPrice", minPrice);
            query.setParameter("maxPrice", maxPrice);

            return query.list();
        }
    }

    public List<Car> findCarsWithPaginationAndSorting(int pageNumber, int pageSize, boolean ascending) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Car c ORDER BY c.price " + (ascending ? "ASC" : "DESC");
            Query<Car> query = session.createQuery(hql, Car.class);
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);
            return query.list();
        }
    }
    public List<Car> findCarsWithEntityGraph() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            EntityGraph<?> entityGraph = session.getEntityGraph("Car.details");
            Query<Car> query = session.createQuery("FROM Car", Car.class);
            query.setHint("javax.persistence.fetchgraph", entityGraph);
            return query.getResultList();
        }
    }

    public void assignCarToShowroom(Car car, CarShowroom showroom) {
        car.setShowroom(showroom);
        addCar(car);
    }
}
