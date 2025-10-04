package com.mycompany.irr00_group_project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a user in the system.
 * @author 2119994 Stilyan Staykov
 * @author 2120887 Martin Beloperkin
 * @author 2087340 Georgi Kolev
 * @author Petar Zhelev
 */
@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // Temporally storing passwords as plain text,
    // later to be updated to hashed password
    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "balance", nullable = false)
    private double balance = 0.0;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>();

    public User() {
    }

    /**
    * Constructs a new User.
    *
    * @param name the user's name
    * @param email the user's email
    * @param password the user's password
    */
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setBalance(double balance) {
        this.balance = balance;
    }
    
    public double getBalance() {
        return balance;
    }
    public List<Booking> getBookings() {
        return bookings;
    }

    /*
    This method should NEVER be used because it can lead to data inconsistency
    It is left and commented for you to see that it should to be implemented nor used
    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
    */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
    * Adds a booking to the user's list of bookings and sets this user on the booking.
    *
    * @param booking the booking to add
    */
    public void addBooking(Booking booking) {
        bookings.add(booking);
        booking.setUser(this);
    }

    /**
    * Removes a booking from the user's list of bookings and unsets this user on the booking.
    *
    * @param booking the booking to remove
    */
    public void removeBooking(Booking booking) {
        bookings.remove(booking);
        booking.setUser(null);
    }

}
