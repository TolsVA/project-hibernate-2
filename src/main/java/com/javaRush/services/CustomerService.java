package com.javaRush.services;

import com.javaRush.dao.DAOFactory;
import com.javaRush.dao.AddressDAO;
import com.javaRush.dao.CityDAO;
import com.javaRush.dao.CustomerDAO;
import com.javaRush.dao.StoreDAO;
import com.javaRush.entity.Address;
import com.javaRush.entity.City;
import com.javaRush.entity.Customer;
import com.javaRush.entity.Store;
import com.javaRush.util.SessionUtil;

public class CustomerService {

    private final CustomerDAO customerDAO;
    private final StoreDAO storeDAO;
    private final AddressDAO addressDAO;
    private final CityDAO cityDAO;
    private final SessionUtil sessionUtil;

    public CustomerService(SessionUtil sessionUtil) {
        this.customerDAO = DAOFactory.getCustomerDAO();
        this.storeDAO = DAOFactory.getStoreDAO();
        this.addressDAO = DAOFactory.getAddressDAO();
        this.cityDAO = DAOFactory.getCityDAO();
        this.sessionUtil = sessionUtil;
    }

    public Customer createCustomer() {
        return sessionUtil.executeInTransaction(_ -> {
            Store store = storeDAO.getItems(0, 1).getFirst();
            City city = cityDAO.getByName("Jakarta");

            Address address = new Address();
            address.setAddress("Bean street,96");
            address.setPhone("999-999-99-99");
            address.setCity(city);
            address.setDistrict("Glory district");
            addressDAO.save(address);

            Customer customer = new Customer();
            customer.setIsActive(true);
            customer.setEmail("test@gmail.com");
            customer.setAddress(address);
            customer.setStore(store);
            customer.setFirstName("Gennady");
            customer.setLastName("Crocodiles");
            customerDAO.save(customer);

            return customer;
        });
    }
}