/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bancodebogota.fdsm.controller;

import com.bancodebogota.fdsm.dao.LoginDao;
import com.bancodebogota.fdsm.dao.LoginDaoImpl;
import com.bancodebogota.fdsm.dao.LoginDaoJpaImpl;
import com.bancodebogota.fdsm.dto.UserDto;
import com.opensymphony.xwork2.ActionSupport;
import javax.servlet.ServletContext;
import org.apache.struts2.util.ServletContextAware;
import org.hibernate.SessionFactory;

/**
 *
 * @author jpramirez
 */
public class LoginAction extends ActionSupport implements ServletContextAware {

    private UserDto userDto;
    private ServletContext ctx;

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    @Override
    public String execute() {
        //userDto = new UserDto();
        LoginDao loginDao =  LoginDaoImpl.getInstance();
        UserDto us =  loginDao.obtenerUsuario(userDto);
        if(us==null){
             return ERROR;
        }else{
            return SUCCESS+"Tiles";
        }     
    }
    
   
    public String executeJpa() {
        //userDto = new UserDto();
        LoginDao loginDao = new LoginDaoJpaImpl(getSF());
        UserDto us = loginDao.obtenerUsuario(userDto);
        if(us==null){
             return ERROR;
        }else{
            return SUCCESS+"Tiles";
        }     
    }

    public void validate() {
        if (userDto.getName().length() == 0) {
            addFieldError("userDto.name", "Name is required.");
        }

        if (userDto.getPassword().length() == 0) {
            addFieldError("userDto.password", "Password is required.");
        }
    }

    @Override
    public void setServletContext(ServletContext sc) {
        this.ctx = sc;
    }
    
    private SessionFactory getSF(){
        SessionFactory sf = (SessionFactory) ctx.getAttribute("SessionFactory");
        return sf;
    }
            
}
