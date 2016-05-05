package com.vutichenko.bqtest.pojo;

import org.h2.util.MathUtils;
import sun.util.calendar.BaseCalendar;

import javax.persistence.*;

/**
 * Created by vutichenko on 04.05.2016.
 */
@javax.persistence.Entity
@Table(name = "SIMPLE_ENTITY")
public class SimpleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ENTID")
    private long entityId;

    @Column(name = "RANDOM_VALUE")
    private String randomValue;

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public String getRandomValue() {
        return randomValue;
    }

    public void setRandomValue(String randomValue) {
        this.randomValue = randomValue;
    }
}
