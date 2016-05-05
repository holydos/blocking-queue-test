package com.vutichenko.bqtest.service;

import com.vutichenko.bqtest.pojo.SimpleEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Iterator;
import java.util.List;

/**
 * Created by vutichenko on 04.05.2016.
 */
public class EntityManagerUtil {
    private static final EntityManagerFactory entityManagerFactory;
    static {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("persistenceUnit");

        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public static SimpleEntity savePojoEntity(String randomValue) {
        EntityManager manager = getEntityManager();
        SimpleEntity student = new SimpleEntity();
        try {
            manager.getTransaction().begin();
            student.setRandomValue(randomValue);
            student = manager.merge(student);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
        }
        return student;
    }

    public static List<SimpleEntity> getEntityList() {
        EntityManager manager = getEntityManager();
        try {
            manager.getTransaction().begin();
            List<SimpleEntity> list = (List<SimpleEntity>) manager.createQuery("FROM SimpleEntity ").getResultList();
            manager.getTransaction().commit();
            return list;
        } catch (Exception e) {
            manager.getTransaction().rollback();
        }
        return null;
    }
}

