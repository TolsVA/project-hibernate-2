package com.javaRush.dao;


import com.javaRush.entity.Actor;
import org.hibernate.SessionFactory;


public class ActorDAO extends GenericDAO<Actor> {
    public ActorDAO( SessionFactory sessionFactory) {
        super(Actor.class, sessionFactory);
    }
}
