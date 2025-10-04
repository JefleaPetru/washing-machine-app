package com.mycompany.irr00_group_project.entity;

import com.mycompany.irr00_group_project.entity.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import java.time.LocalDateTime;

/**
 * Entity representing a booking in the system.
 * @author 2119994 Stilyan Staykov
 * @author 2120887 Martin Beloperkin
 * @author 2087340 Georgi Kolev
 * @author Petar Zhelev
 */
@Entity
@Table(name = "BOOKING")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    // Many bookings belong to one user
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Many bookings belong to one washing machine
    @ManyToOne(optional = false)
    @JoinColumn(name = "washing_machine_id", nullable = false)
    private WashingMachine washingMachine;

    @Column(name = "booking_start", nullable = false)
    private LocalDateTime bookingStart;

    @Column(name = "booking_end", nullable = false)
    private LocalDateTime bookingEnd;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    // Constructors
    public Booking() {}

    /**
    * Constructs a Booking with the specified user, 
    * washing machine, booking start and end, and status.
    *
    * @param user the user who made the booking
    * @param washingMachine the washing machine being booked
    * @param bookingStart the start time of the booking
    * @param bookingEnd the end time of the booking
    * @param status the status of the booking
    */
    public Booking(User user, WashingMachine washingMachine, LocalDateTime bookingStart,
                   LocalDateTime bookingEnd, Status status) {
        this.user = user;
        this.washingMachine = washingMachine;
        this.bookingStart = bookingStart;
        this.bookingEnd = bookingEnd;
        this.status = status;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public WashingMachine getWashingMachine() {
        return washingMachine;
    }

    public void setWashingMachine(WashingMachine washingMachine) {
        this.washingMachine = washingMachine;
    }

    public LocalDateTime getBookingStart() {
        return bookingStart;
    }

    public void setBookingStart(LocalDateTime bookingStart) {
        this.bookingStart = bookingStart;
    }

    public LocalDateTime getBookingEnd() {
        return bookingEnd;
    }

    public void setBookingEnd(LocalDateTime bookingEnd) {
        this.bookingEnd = bookingEnd;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

