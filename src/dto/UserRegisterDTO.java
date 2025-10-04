package com.mycompany.irr00_group_project.dto;

/**
 * Data Transfer Object for user registration.
 * @author 2119994 Stilyan Staykov
 * @author 2120887 Martin Beloperkin
 * @author 2087340 Georgi Kolev
 * @author Petar Zhelev
 */
public class UserRegisterDTO {
    private String name;
    private String email;
    private String password;

    public UserRegisterDTO() {}

    /**
    * Constructs a new UserRegisterDTO.
    *
    * @param name the user's name
    * @param email the user's email
    * @param password the user's password
    */
    public UserRegisterDTO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
