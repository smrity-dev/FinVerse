package com.finverse.dao;

import com.finverse.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAOImpl implements AdminDAO {

    @Override
    public boolean login(String username, String password) {

        String sql =
                "SELECT * FROM admins WHERE username=? AND password=?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}