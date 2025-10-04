package com.mycompany.irr00_group_project.security;

/**
 * @author 2119994 Stilyan Staykov
 */
public interface PasswordHasher {

    public String hash(String password);
    public boolean verify(String password, String hashedPassword);
}
