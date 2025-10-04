package com.mycompany.irr00_group_project.security;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * @author 2119994 Stilyan Staykov
 */
public class BCryptPasswordHasher implements PasswordHasher {

    private final int cost;

    // This cost is preferred because it is a good balance between security and performance
    public BCryptPasswordHasher() {
        this.cost = 12;
    }

    public BCryptPasswordHasher(int cost) {
        this.cost = cost;
    }

    @Override
    public String hash(String password) {
        return BCrypt.withDefaults().hashToString(cost, password.toCharArray());
    }

    @Override
    public boolean verify(String password, String hashedPassword) {
        BCrypt.Result result = BCrypt.verifyer()
                .verify(password.toCharArray(), hashedPassword.toCharArray());
        return result.verified;
    }
}
