package org.example;

import org.example.entity.*;
import org.example.service.CarService;
import org.example.service.ClientService;
import org.example.service.ReviewService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        CarService carService = new CarService();
        ClientService clientService = new ClientService();
        ReviewService reviewService = new ReviewService();

        Category sedanCategory = new Category();
        sedanCategory.setName("Sedan");

        Category suvCategory = new Category();
        suvCategory.setName("SUV");

        carService.addCategory(sedanCategory);
        carService.addCategory(suvCategory);

        CarShowroom showroom1 = new CarShowroom();
        showroom1.setName("Luxury Cars");
        showroom1.setAddress("123 Luxury St");

        CarShowroom showroom2 = new CarShowroom();
        showroom2.setName("Economy Cars");
        showroom2.setAddress("456 Economy St");

        carService.addCarShowroom(showroom1);
        carService.addCarShowroom(showroom2);

        Car car1 = new Car();
        car1.setModel("BMW 3 Series");
        car1.setBrand("BMW");
        car1.setManufactureYear(2022);
        car1.setPrice(50000.00);
        car1.setCategory(sedanCategory);
        car1.setShowroom(showroom1);

        Car car2 = new Car();
        car2.setModel("Toyota RAV4");
        car2.setBrand("Toyota");
        car2.setManufactureYear(2023);
        car2.setPrice(30000.00);
        car2.setCategory(suvCategory);
        car2.setShowroom(showroom2);

        carService.addCar(car1);
        carService.addCar(car2);

        Client client = new Client();
        client.setName("Client Client1");
        client.setRegistrationDate(java.time.LocalDate.now());
        clientService.addClient(client);

        clientService.buyCar(client, car1);


        reviewService.addReview(client, car1, "Great car, smooth ride!", 5);

        List<Car> foundCars = carService.findCarsByFilters("Toyota", "SUV", 2020, 25000, 35000);
        System.out.println("Found Cars: ");
        for (Car car : foundCars) {
            System.out.println(car.getModel() + " - " + car.getBrand() + " - $" + car.getPrice());
        }

        List<Review> reviews = reviewService.searchReviews("smooth");
        System.out.println("\nFound Reviews: ");
        for (Review review : reviews) {
            System.out.println("Review: " + review.getText() + " | Rating: " + review.getRating());
        }
        if (clientService.isEligibleForLoyaltyProgram(client)) {
            System.out.println("\nClient is eligible for loyalty program!");
        } else {
            System.out.println("\nClient is not eligible for loyalty program.");
        }
    }
}
