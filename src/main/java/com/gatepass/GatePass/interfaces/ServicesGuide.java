package com.gatepass.GatePass.interfaces;

import com.gatepass.GatePass.dtos.Auth;
import com.gatepass.GatePass.entities.MyUser;

import jakarta.servlet.http.HttpServletResponse;

public interface ServicesGuide {
    public MyUser getMyUser(String username);
    public MyUser registerAdminMyUser(MyUser myUser, HttpServletResponse response);
    public MyUser registerRepMyUser(MyUser myUser, HttpServletResponse response);
    public void login(Auth auth, HttpServletResponse response);
}
