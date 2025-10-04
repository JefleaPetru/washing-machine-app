package com.mycompany.irr00_group_project.dto;

/**
 * Data Transfer Object for user information.
 * @author 2119994 Stilyan Staykov
 * @author 2120887 Martin Beloperkin
 * @author 2087340 Georgi Kolev
 * @author Petar Zhelev
 */
public class UserDTO {
    private long id;
    private String name;
    private String email;

    public UserDTO() {}

    /**
    * Constructs a new UserDTO.
    *
    * @param id the user ID
    * @param name the user name
    * @param email the user email
    */
    public UserDTO(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}
