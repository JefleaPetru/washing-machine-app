package com.mycompany.irr00_group_project.mapper;

import com.mycompany.irr00_group_project.dao.UserDAO;
import com.mycompany.irr00_group_project.dao.WashingMachineDAO;
import com.mycompany.irr00_group_project.dto.BookingRegisterDTO;
import com.mycompany.irr00_group_project.entity.Booking;
import com.mycompany.irr00_group_project.entity.User;
import com.mycompany.irr00_group_project.entity.WashingMachine;
import com.mycompany.irr00_group_project.entity.enums.Status;

/**
 * @author 2119994 Stilyan Staykov
 * @author 2120887 Martin Beloperkin
 * @author 2087340 Georgi Kolev
 * @author Petar Zhelev
 */
public class BookingRegisterDTOMapper {

    private BookingRegisterDTOMapper() {
    }

    public static Booking toBookingEntity(BookingRegisterDTO dto, UserDAO userDAO, WashingMachineDAO wmDAO)
            throws IllegalAccessException {
        User user = userDAO.findById(dto.getUserId());
        WashingMachine wm = wmDAO.findById(dto.getWashingMachineId());

        if (user == null || wm == null) {
            throw new IllegalArgumentException("User or WashingMachine not found.");
        }

        return new Booking(user, wm, dto.getBookingStart(), dto.getBookingEnd(), Status.NOT_STARTED);
    }
}
