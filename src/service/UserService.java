package com.mycompany.irr00_group_project.service;

import com.mycompany.irr00_group_project.dao.UserDAO;
import com.mycompany.irr00_group_project.dto.UserLoginDTO;
import com.mycompany.irr00_group_project.dto.UserRegisterDTO;
import com.mycompany.irr00_group_project.entity.User;
import com.mycompany.irr00_group_project.security.BCryptPasswordHasher;
import com.mycompany.irr00_group_project.security.PasswordHasher;

import java.util.Optional;

/**
 * @author 2119994 Stilyan Staykov
 */
public class UserService {

    private UserDAO userDAO;
    private PasswordHasher hasher;

    public UserService() {
    }

    public UserService(UserDAO userDAO, BCryptPasswordHasher hasher) {
        this.userDAO = userDAO;
        this.hasher = hasher;
    }

    public boolean registerUser(UserRegisterDTO userRegisterDTO) {
        Optional<User> userOpt = userDAO.findByEmail(userRegisterDTO.getEmail());
        if (userOpt.isPresent()) {
            return false;
        }

        User user = new User(
                userRegisterDTO.getName(),
                userRegisterDTO.getEmail(),
                hasher.hash(userRegisterDTO.getPassword()));
        userDAO.save(user);

        return true;
    }

    public Optional<User> login(UserLoginDTO userLoginDTO) {
        Optional<User> userOpt = userDAO.findByEmail(userLoginDTO.getEmail());
        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        User user = userOpt.get();
        if (hasher.verify(userLoginDTO.getPassword(), user.getPassword())) {
            return Optional.of(user);
        }

        return Optional.empty();
    }

    public Optional<User> findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    public void decreaseBalance(int userId, double amount) {
        userDAO.decreaseBalance(userDAO.findById(userId), amount);
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public PasswordHasher getHasher() {
        return hasher;
    }

    public void setHasher(PasswordHasher hasher) {
        this.hasher = hasher;
    }
}
