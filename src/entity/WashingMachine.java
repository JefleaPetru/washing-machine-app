package com.mycompany.irr00_group_project.entity;

import com.mycompany.irr00_group_project.entity.enums.Status;
import com.mycompany.irr00_group_project.entity.enums.WashingMachineModel;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 2119994 Stilyan Staykov
 * @author 2120887 Martin Beloperkin
 * @author 2087340 Georgi Kolev
 * @author Petar Zhelev
 */
@Entity
@Table(name = "WASHING_MACHINE")
public class WashingMachine {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "model", nullable = false)
    private WashingMachineModel model;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "serial_number", nullable = false)
    private String serialNumber;

    // One WashingMachine can have multiple bookings
    @OneToMany(mappedBy = "washingMachine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>();

    // Constructors
    public WashingMachine() {
    }

    public WashingMachine(WashingMachineModel model, String serialNumber) {
        this.model = model;
        this.serialNumber = serialNumber;
        this.status = Status.NOT_STARTED;
    }

    public Status getStatus() { return this.status; }

    public void setStatus(Status status) { this.status = status; }

    public Long getId() {
        return id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WashingMachineModel getModel() {
        return model;
    }

    public void setModel(WashingMachineModel model) {
        this.model = model;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

//    This method should NEVER be used because it can lead to data inconsistency
//    It is left and commented for you to see that it should to be implemented nor used
//    public void setBookings(List<Booking> bookings) {
//        this.bookings = bookings;
//    }

    // Methods to manage bidirectional relationship
    public void addBooking(Booking booking) {
        bookings.add(booking);
        booking.setWashingMachine(this);
    }

    public void removeBooking(Booking booking) {
        bookings.remove(booking);
        booking.setWashingMachine(null);
    }
}
