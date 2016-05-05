package com.vutichenko.bqtest.service;

import com.sun.istack.internal.NotNull;
import com.vutichenko.bqtest.pojo.SimpleEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Collections;
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

    public static int entityQuantity() {
        return getEntityList().size();
    }

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public synchronized SimpleEntity savePojoEntity(String randomValue, int limit) throws InterruptedException {
        EntityManager manager = getEntityManager();

        System.out.println("start save");
        Thread.sleep(2000);
        if (getEntityList().size() == limit) {
            System.out.println("save notify");
            notifyAll();
        }
        while (getEntityList().size() == limit) {
            System.out.println("limit size");

            wait();
        }
        SimpleEntity student = new SimpleEntity();
        try {
            manager.getTransaction().begin();
            student.setRandomValue(randomValue);
            student = manager.merge(student);
            manager.getTransaction().commit();
            System.out.println("saved id " + student.getEntityId());
        } catch (Exception e) {
            manager.getTransaction().rollback();
        }
        return student;
    }

    public synchronized void removeSimpleEntity(Long entityId, int limit) throws InterruptedException {
        EntityManager entityManager = getEntityManager();

        System.out.println("start remove");
        Thread.sleep(2000);

        if (getEntityList().size() < limit) {
            System.out.println("remove notify");
            notifyAll();
        }

        while (getEntityList().size() == 0) {
            System.out.println("zero size");
            wait();
        }

        try {
            entityManager.getTransaction().begin();
            SimpleEntity simpleEntity = (SimpleEntity) entityManager.find(SimpleEntity.class, entityId);
            entityManager.remove(simpleEntity);
            entityManager.getTransaction().commit();
            System.out.println("removed" + simpleEntity.getEntityId());
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }

    }

    @NotNull
    public static List<SimpleEntity> getEntityList() {
        EntityManager manager = getEntityManager();
        try {
            manager.getTransaction().begin();
            List<SimpleEntity> list = (List<SimpleEntity>) manager.createQuery("FROM SimpleEntity ").getResultList();
            if (list == null) throw new IllegalStateException("something went wrong");
            manager.getTransaction().commit();
            return list;
        } catch (Exception e) {
            manager.getTransaction().rollback();
        }
        return Collections.emptyList();
    }
}

