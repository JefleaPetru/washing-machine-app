package com.mycompany.irr00_group_project.dto;

/**
 * Data Transfer Object for user login credentials.
 * @author 2119994 Stilyan Staykov
 * @author 2120887 Martin Beloperkin
 * @author 2087340 Georgi Kolev
 * @author Petar Zhelev
 */
public class UserLoginDTO {
    private String email;
    private String password;

    public UserLoginDTO() {
    }

    /**
    * Constructs a new UserLoginDTO.
    *
    * @param email the user's email
    * @param password the user's password
    */
    public UserLoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
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
