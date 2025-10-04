package com.mycompany.irr00_group_project.service;

import com.mycompany.irr00_group_project.dao.WashingMachineDAO;
import com.mycompany.irr00_group_project.dto.WashingMachineRegisterDTO;
import com.mycompany.irr00_group_project.entity.Booking;
import com.mycompany.irr00_group_project.entity.WashingMachine;
import com.mycompany.irr00_group_project.mapper.WashingMachineRegisterDTOMapper;

import java.time.LocalDateTime;
import java.util.Optional;

public class WashingMachineService {
    private WashingMachineDAO washingMachineDAO;

    public WashingMachineService(WashingMachineDAO washingMachineDAO) {
        this.washingMachineDAO = washingMachineDAO;
    }

    public boolean register(WashingMachineRegisterDTO washingMachineRegisterDTO) {
        Optional<WashingMachine> washingMachineOpt = washingMachineDAO
                .findBySerialNumber(washingMachineRegisterDTO.getSerialNumber());
        if (washingMachineOpt.isPresent()) {
            return false;
        }

        WashingMachine washingMachine = WashingMachineRegisterDTOMapper
                .mapToWashingMachineEntity(washingMachineRegisterDTO);
        washingMachineDAO.save(washingMachine);

        return true;
    }

    public Boolean isWashingMachineBookedByUser(int userId, int washingMachineId) {
        WashingMachine washingMachine = this.washingMachineDAO.findById(washingMachineId);
        var bookings = washingMachine.getBookings();
        for (var booking : bookings) {
            if (booking.getUser().getId().intValue() == userId
                    && booking.getBookingStart().isBefore(LocalDateTime.now())
                    && booking.getBookingEnd().isAfter(LocalDateTime.now())) {
                return true;
            }
        }
        return false;
    }

    public Booking getCurrentBooking(int washingMachineId) {
        WashingMachine washingMachine = this.washingMachineDAO.findById(washingMachineId);
        for(var booking : washingMachine.getBookings()) {
            if(booking.getBookingStart().isBefore(LocalDateTime.now())
                    && booking.getBookingEnd().isAfter(LocalDateTime.now())) {
                return booking;
            }
        }
        return null;
    }

    public WashingMachineDAO getWashingMachineDAO() {
        return washingMachineDAO;
    }

    public void setWashingMachineDAO(WashingMachineDAO washingMachineDAO) {
        this.washingMachineDAO = washingMachineDAO;
    }
}
