package com.finverse.service;

import com.finverse.dao.AdminDAO;
import com.finverse.dao.AdminDAOImpl;

public class AdminService {

    private static AdminService instance;

    private final AdminDAO adminDAO =
            new AdminDAOImpl();

    private AdminService(){}

    public static AdminService getInstance(){

        if(instance==null){
            instance=new AdminService();
        }

        return instance;
    }

    public boolean login(String username,
                         String password){

        if(username==null || username.isBlank()){
            return false;
        }

        if(password==null || password.isBlank()){
            return false;
        }

        return adminDAO.login(
                username.trim(),
                password
        );
    }
}