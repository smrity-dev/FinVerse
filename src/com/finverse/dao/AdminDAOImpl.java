package com.finverse.dao;

public class AdminDAOImpl implements AdminDAO{

    private final String username="admin";
    private final String password="admin123";

    @Override
    public boolean login(String username, String password)
    {
        return this.username.equals(username) && this.password.equals(password);
    }
}
