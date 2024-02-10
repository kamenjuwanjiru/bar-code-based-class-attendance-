package com.gatepass.GatePass.interfaces;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.gatepass.GatePass.dtos.Auth;
import com.gatepass.GatePass.dtos.MajorQuery;
import com.gatepass.GatePass.entities.History;
import com.gatepass.GatePass.entities.MyUser;
import com.gatepass.GatePass.entities.Personel;

import jakarta.servlet.http.HttpServletResponse;

public interface ServicesGuide {
    public MyUser getMyUser(String username);
    public MyUser registerAdminMyUser(MyUser myUser, HttpServletResponse response);
    public MyUser registerRepMyUser(MyUser myUser, HttpServletResponse response);
    public void login(Auth auth, HttpServletResponse response);
    public Personel addPersonel(MultipartFile file, String data, HttpServletResponse response);
    public Personel getPersonel(MajorQuery majorQuery);
    public History addHistory(History history);
    public List<History> getHistory(MajorQuery majorQuery);
}
