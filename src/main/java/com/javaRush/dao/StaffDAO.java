package com.javaRush.dao;

import com.javaRush.entity.Staff;
import org.hibernate.SessionFactory;

public class StaffDAO extends GenericDAO<Staff> {
    public StaffDAO(SessionFactory sessionFactory) {
        super(Staff.class, sessionFactory);
    }
}