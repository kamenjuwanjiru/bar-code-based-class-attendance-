package com.gatepass.GatePass.interfaces;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.gatepass.GatePass.dtos.Auth;
import com.gatepass.GatePass.dtos.MajorQuery;
import com.gatepass.GatePass.dtos.PassUpdate;
import com.gatepass.GatePass.entities.History;
import com.gatepass.GatePass.entities.MyUser;
import com.gatepass.GatePass.entities.Personel;
import com.gatepass.GatePass.entities.Unit;

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
    public void removePersonel(MajorQuery majorQuery, HttpServletResponse response);
    public Unit addOrUpdateUnit(Unit unit);
    public String getStaffNumber();
    public List<Unit> getUnits();
    public boolean updatePassword(PassUpdate passUpdate);
}
