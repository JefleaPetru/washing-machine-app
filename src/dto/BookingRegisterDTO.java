package com.mycompany.irr00_group_project.dto;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for registering a washing machine booking.
 * Holds user ID, machine ID, and booking time interval.
 * @author 2119994 Stilyan Staykov
 * @author 2120887 Martin Beloperkin
 * @author 2087340 Georgi Kolev
 * @author Petar Zhelev
 */
public class BookingRegisterDTO {
    private Long userId;
    private Long washingMachineId;
    private LocalDateTime bookingStart;
    private LocalDateTime bookingEnd;

    public BookingRegisterDTO() {
    }

    /**
    * Constructor for BookingRegisterDTO.
    *
    * @param userId the ID of the user
    * @param washingMachineId the ID of the washing machine
    * @param bookingStart the start time of the booking
    * @param bookingEnd the end time of the booking
    */
    public BookingRegisterDTO(
            Long userId, 
            Long washingMachineId, 
            LocalDateTime bookingStart, 
            LocalDateTime bookingEnd
    ) {
        this.userId = userId;
        this.washingMachineId = washingMachineId;
        this.bookingStart = bookingStart;
        this.bookingEnd = bookingEnd;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getWashingMachineId() {
        return washingMachineId;
    }

    public void setWashingMachineId(Long washingMachineId) {
        this.washingMachineId = washingMachineId;
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
}
