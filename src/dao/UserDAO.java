package com.mycompany.irr00_group_project.dao;

import com.mycompany.irr00_group_project.entity.Booking;
import com.mycompany.irr00_group_project.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object (DAO) for managing {@link User} entities.
 * Provides methods to save, delete, and retrieve users from FileDb.
 *
 * @author 2119994 Stilyan Staykov
 * @author 2120887 Martin Beloperkin
 * @author 2087340 Georgi Kolev
 * @author Petar Zhelev
 */

public class UserDAO extends GenericDAO<User> {

    public UserDAO() {
        super(User.class);
    }

    @Override
    public void save(User entity) {
        fileDb.add(entity);
    }

    @Override
    public void delete(User entity) {
        fileDb.delete(entity);
    }

    @Override
    public User findById(long id) {
        return fileDb.findUserById(id);
    }

    public List<User> findAll() {
        return fileDb.selectAllUsers();
    }

    /**
    * Finds a user by their email address.
    *
    * @param email the email address to search for
    * @return an Optional containing the matching user, or empty if not found
    */
    public Optional<User> findByEmail(String email) {
        return fileDb.selectAllUsers().stream()
            .filter(u -> u.getEmail().equalsIgnoreCase(email))
            .findFirst();
    }

    public void increaseBalance(User user, double amount) {
        user.setBalance(user.getBalance() + amount);
        save(user);
    }

    public void decreaseBalance(User user, double amount) {
        user.setBalance(user.getBalance() - amount);
        save(user);
    }

}
