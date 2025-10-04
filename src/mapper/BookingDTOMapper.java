package com.mycompany.irr00_group_project.mapper;

import com.mycompany.irr00_group_project.dto.BookingDTO;
import com.mycompany.irr00_group_project.entity.Booking;

/**
 * @author 2119994 Stilyan Staykov
 * @author 2120887 Martin Beloperkin
 * @author 2087340 Georgi Kolev
 * @author Petar Zhelev
 */
public class BookingDTOMapper {
    private BookingDTOMapper() {
    }

    public static BookingDTO mapBookingToDTO(Booking booking) {
        return new BookingDTO(
                booking.getId(),
                booking.getUser().getId(),
                booking.getWashingMachine().getId(),
                booking.getBookingStart(),
                booking.getBookingEnd(),
                booking.getStatus()
        );
    }
}
