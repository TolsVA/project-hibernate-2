package com.javaRush.services;

import com.javaRush.dao.DAOFactory;
import com.javaRush.dao.*;
import com.javaRush.entity.*;
import com.javaRush.util.SessionUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RentalService {

    private final FilmDAO filmDAO;
    private final InventoryDAO inventoryDAO;
    private final RentalDAO rentalDAO;
    private final PaymentDAO paymentDAO;
    private final StoreDAO storeDAO;
    private final SessionUtil sessionUtil;

    public RentalService(SessionUtil sessionUtil) {
        this.filmDAO = DAOFactory.getFilmDAO();
        this.inventoryDAO = DAOFactory.getInventoryDAO();
        this.rentalDAO = DAOFactory.getRentalDAO();
        this.paymentDAO = DAOFactory.getPaymentDAO();
        this.storeDAO = DAOFactory.getStoreDAO();
        this.sessionUtil = sessionUtil;
    }

    public void rentInventory(Customer customer) {
        sessionUtil.executeInTransaction(_ -> {
            Film film = filmDAO.getFirstAvailableFilmForRent();

            Inventory inventory = new Inventory();
            Store store = storeDAO.getItems(0, 1).getFirst();
            inventory.setFilm(film);
            inventory.setStore(store);
            inventoryDAO.save(inventory);

            Staff staff = store.getManagerStaff();

            Rental rental = new Rental();
            rental.setRentalDate(LocalDateTime.now());
            rental.setCustomer(customer);
            rental.setInventory(inventory);
            rental.setStaff(staff);
            rentalDAO.save(rental);

            Payment payment = new Payment();
            payment.setRental(rental);
            payment.setStaff(staff);
            payment.setAmount(BigDecimal.valueOf(55.77));
            payment.setPaymentDate(LocalDateTime.now());
            payment.setCustomer(customer);
            paymentDAO.save(payment);

            return null;
        });
    }

    public void returnInventory() {
        sessionUtil.executeInTransaction(_ -> {
            Rental rental = rentalDAO.getAnyUnreturnedRental();
            rental.setReturnDate(LocalDateTime.now());
            rentalDAO.save(rental);

            return null;
        });
    }
}