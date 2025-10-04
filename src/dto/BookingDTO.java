package com.mycompany.irr00_group_project.dto;

import com.mycompany.irr00_group_project.entity.enums.Status;

import java.time.LocalDateTime;

/**
 * Data Transfer Object representing booking details for transfer between layers.
 *
 * @author 2119994 Stilyan Staykov
 * @author 2120887 Martin Beloperkin
 * @author 2087340 Georgi Kolev
 * @author Petar Zhelev
 */
public class BookingDTO {
    private long id;
    private long userId;
    private long washingMachineId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Status status;
    
    /**
     * Default constructor.
     */
    public BookingDTO() {
    }
    
    /**
     * Constructs a new BookingDTO with all required fields.
     *
     * @param id                the booking ID
     * @param userId            the ID of the user who made the booking
     * @param washingMachineId  the ID of the washing machine booked
     * @param startTime         the start time of the booking
     * @param endTime           the end time of the booking
     * @param status            the status of the booking
     */
    public BookingDTO(
            long id, 
            long userId, 
            long washingMachineId, 
            LocalDateTime startTime, 
            LocalDateTime endTime, 
            Status status
    ) {
        this.id = id;
        this.userId = userId;
        this.washingMachineId = washingMachineId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }
    
    /**
     * Returns the booking’s unique identifier.
     *
     * @return the booking ID
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the booking’s unique identifier.
     *
     * @param id the booking ID to set
     */ 
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns the ID of the user who made the booking.
     *
     * @return the user ID
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets the ID of the user who made the booking.
     *
     * @param userId the user ID to set
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Returns the ID of the washing machine booked.
     *
     * @return the washing machine ID
     */
    public long getWashingMachineId() {
        return washingMachineId;
    }

    /**
     * Sets the ID of the washing machine booked.
     *
     * @param washingMachineId the washing machine ID to set
     */
    public void setWashingMachineId(long washingMachineId) {
        this.washingMachineId = washingMachineId;
    }

    /**
     * Returns the start time of the booking.
     *
     * @return the booking start time
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of the booking.
     *
     * @param startTime the booking start time to set
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Returns the end time of the booking.
     *
     * @return the booking end time
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of the booking.
     *
     * @param endTime the booking end time to set
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Returns the status of the booking.
     *
     * @return the booking status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the status of the booking.
     *
     * @param status the booking status to set
     */
    public void setStatus(Status status) {
        this.status = status;
    }
}
