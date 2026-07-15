package com.finverse.service;

import com.finverse.dao.AdminDAO;
import com.finverse.dao.AdminDAOImpl;

public class AdminService {

    private static AdminService instance;
    private final AdminDAO adminDAO = new AdminDAOImpl();
    private AdminService(){}
    public static AdminService getInstance(){
        if(instance==null){
            instance=new AdminService();
        }
        return instance;
    }
    public boolean login(String username, String password){
        return adminDAO.login(username,password);
    }
}