package com.mycompany.irr00_group_project.service;

import com.mycompany.irr00_group_project.dao.BookingDAO;
import com.mycompany.irr00_group_project.dao.UserDAO;
import com.mycompany.irr00_group_project.dao.WashingMachineDAO;
import com.mycompany.irr00_group_project.entity.Booking;
import com.mycompany.irr00_group_project.entity.User;
import com.mycompany.irr00_group_project.entity.WashingMachine;
import com.mycompany.irr00_group_project.entity.enums.Status;

import java.time.LocalDateTime;

public class BookingService {
    private final BookingDAO bookingDAO;
    private final UserDAO userDAO;
    private final WashingMachineDAO washingMachineDAO;

    public  BookingService() {
        bookingDAO = new BookingDAO();
        userDAO = new UserDAO();
        washingMachineDAO = new WashingMachineDAO();
    }

    public void SaveBookingService(long userId, long washingMachineId, boolean dryingSelected) {
        User user = userDAO.findById(userId);
        WashingMachine washingMachine = washingMachineDAO.findById(washingMachineId);

        if (dryingSelected) {
            Booking booking = new Booking(user, washingMachine, LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(40), Status.IN_PROGRESS);
            bookingDAO.save(booking);
        }
        else {
            Booking booking = new Booking(user, washingMachine, LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(30), Status.IN_PROGRESS);
            bookingDAO.save(booking);
        }
    }
}
