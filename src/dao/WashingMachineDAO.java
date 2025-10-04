package com.mycompany.irr00_group_project.dao;

import com.mycompany.irr00_group_project.entity.Booking;
import com.mycompany.irr00_group_project.entity.WashingMachine;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object (DAO) for managing {@link WashingMachine} entities.
 * Provides methods to save, delete, retrieve, and search washing machines from FileDb.
 *
 * @author 2119994 Stilyan Staykov
 * @author 2120887 Martin Beloperkin
 * @author 2087340 Georgi Kolev
 * @author Petar Zhelev
 */

public class WashingMachineDAO extends GenericDAO<WashingMachine> {

    public WashingMachineDAO() {
        super(WashingMachine.class);
    }

    @Override
    public void save(WashingMachine entity) {
        fileDb.add(entity);
    }

    @Override
    public void delete(WashingMachine entity) {
        fileDb.delete(entity);
    }

    @Override
    public WashingMachine findById(long id) {
        return fileDb.findWashingMachineById(id);
    }

    public List<WashingMachine> findAll() {
        return fileDb.selectAllWashingMachines();
    }
    
//    public String getSerialNumber() {
//        return fileDb.serialNumber;
//    }

    /**
    * Finds a washing machine by its serial number.
    *
    * @param serialNumber the serial number to search for
    * @return an Optional containing the matching machine, or empty if not found
    */
    public Optional<WashingMachine> findBySerialNumber(String serialNumber) {
        return fileDb.selectAllWashingMachines().stream()
               .filter(w -> w.getSerialNumber().equalsIgnoreCase(serialNumber))
               .findFirst();
    }

}
