package com.mycompany.irr00_group_project.dao;

import com.mycompany.irr00_group_project.entity.Booking;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * Data Access Object (DAO) for managing {@link Booking} entities.
 * Provides methods to save, delete, and query booking data.
 *
 * @author 2119994 Stilyan Staykov
 * @author 2120887 Martin Beloperkin
 * @author 2087340 Georgi Kolev
 * @author Petar Zhelev
 */
public class BookingDAO extends GenericDAO<Booking> {

    public BookingDAO() {
        super(Booking.class);
    }

    @Override
    public void save(Booking entity) {
        fileDb.add(entity);
    }

    @Override
    public void delete(Booking entity) {
        fileDb.delete(entity);
    }

    @Override
    public Booking findById(long id) {
        return fileDb.findBookingById(id);
    }

    public List<Booking> findAll() {
        return fileDb.selectAllBookings();
    }

    //TODO: Make logic for those methods
    //public List<Booking> findByUserId(Long id) {
    //
    //}
    //
    //public List<Booking> findByWashingMachineId(Long id) {
    //
    //}
}
