package com.example.application;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Digits;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
public class Run {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String car;
    private String driver;
    private String track;
    private String lane;
    @Digits(integer = 2, fraction = 3)
    private BigDecimal dial;
    @Digits(integer = 2, fraction = 3)
    private BigDecimal reaction;
    @Digits(integer = 2, fraction = 3)
    private BigDecimal sixtyFoot;
    @Digits(integer = 2, fraction = 3)
    private BigDecimal halfTrack;
    @Digits(integer = 2, fraction = 3)
    private BigDecimal fullTrack;
    @Digits(integer = 3, fraction = 3)
    private BigDecimal speed;

    public Run(LocalDate date, LocalTime time, String car, String driver, String track, String lane, BigDecimal dial, BigDecimal reaction, BigDecimal sixtyFoot, BigDecimal halfTrack, BigDecimal fullTrack, BigDecimal speed) {
        this.date = date;
        this.time = time;
        this.car = car;
        this.driver = driver;
        this.track = track;
        this.lane = lane;
        this.dial = dial;
        this.reaction = reaction;
        this.sixtyFoot = sixtyFoot;
        this.halfTrack = halfTrack;
        this.fullTrack = fullTrack;
        this.speed = speed;
    }

    public Run() {

    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getReaction() {
        return reaction;
    }

    public void setReaction(BigDecimal reaction) {
        this.reaction = reaction;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getLane() {
        return lane;
    }

    public void setLane(String lane) {
        this.lane = lane;
    }

    public BigDecimal getDial() {
        return dial;
    }

    public void setDial(BigDecimal dial) {
        this.dial = dial;
    }

    public BigDecimal getSixtyFoot() {
        return sixtyFoot;
    }

    public void setSixtyFoot(BigDecimal sixtyFoot) {
        this.sixtyFoot = sixtyFoot;
    }

    public BigDecimal getHalfTrack() {
        return halfTrack;
    }

    public void setHalfTrack(BigDecimal halfTrack) {
        this.halfTrack = halfTrack;
    }

    public BigDecimal getFullTrack() {
        return fullTrack;
    }

    public void setFullTrack(BigDecimal fullTrack) {
        this.fullTrack = fullTrack;
    }

    public BigDecimal getSpeed() {
        return speed;
    }

    public void setSpeed(BigDecimal speed) {
        this.speed = speed;
    }
}
