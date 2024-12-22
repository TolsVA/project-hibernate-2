package com.javaRush;

import com.javaRush.dao.DAOFactory;
import com.javaRush.entity.*;
import com.javaRush.services.CustomerService;
import com.javaRush.services.FilmService;
import com.javaRush.services.RentalService;
import com.javaRush.config.HibernateUtil;
import com.javaRush.util.SessionUtil;

public class Main {

    public static void main(String[] args) {
        SessionUtil sessionUtil = new SessionUtil(HibernateUtil.getSessionFactory());

        CustomerService customerService = new CustomerService(sessionUtil);
        RentalService rentalService = new RentalService(sessionUtil);
        FilmService filmService = new FilmService(sessionUtil);

        Customer customer = customerService.createCustomer();
        rentalService.returnInventory();
        rentalService.rentInventory(customer);
        filmService.addNewFilm();
    }
}
