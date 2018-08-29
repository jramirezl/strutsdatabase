/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bancodebogota.fdsm.dao;

import com.bancodebogota.fdsm.config.SimpleDataSource;
import com.bancodebogota.fdsm.dto.UserDto;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

/**
 *
 * @author jpramirez
 */
public class LoginDaoImpl implements LoginDao{

    private static LoginDao loginDao = null;

    private LoginDaoImpl() {
    }

    public static LoginDao getInstance() {
        if (loginDao == null) {
            loginDao = new LoginDaoImpl();
        }
        return loginDao;
    }

    public UserDto obtenerUsuario(UserDto userDto) {

        String query = "select name, login, password, email from users where login = "
                + userDto.getLogin() + " and password = " + userDto.getPassword();

        DataSource dataSource = SimpleDataSource.setupDataSource();

        Connection conn = null;
        Statement stmt = null;
        ResultSet rset = null;

        try {

            conn = dataSource.getConnection();
            stmt = conn.createStatement();
            rset = stmt.executeQuery(query);

            if (rset.next()) {
                userDto.setEmail(rset.getString("email"));
                userDto.setName(rset.getString("name"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rset != null) {
                    rset.close();
                }
            } catch (Exception e) {
            }
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception e) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
            }
        }

        return userDto;
    }

}
