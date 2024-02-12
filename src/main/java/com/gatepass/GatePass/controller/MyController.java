package com.gatepass.GatePass.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gatepass.GatePass.dtos.Auth;
import com.gatepass.GatePass.dtos.MajorQuery;
import com.gatepass.GatePass.entities.History;
import com.gatepass.GatePass.entities.MyUser;
import com.gatepass.GatePass.entities.Personel;
import com.gatepass.GatePass.interfaces.ServicesGuide;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
public class MyController {
    @Autowired
    ServicesGuide servicesGuide;

 

    @PostMapping("/admin/register/{who}")
    public MyUser register(@PathVariable(value = "who") String who,@RequestBody MyUser user, HttpServletResponse response){
        if(who.equals("admin")){
            return servicesGuide.registerAdminMyUser(user, response);
        }else if(who.equals("rep")){
            return servicesGuide.registerRepMyUser(user, response);
        }else{
            log.warn("who is being registered");
            return null;
        }
    }

    @PostMapping("/any/login")
    public void login(@RequestBody Auth auth, HttpServletResponse response){
        servicesGuide.login(auth, response);
    }

    @PostMapping("/addpersonel")
    public Personel addPersonel(@RequestPart(value = "image") MultipartFile file,@RequestPart(value = "data") String data, HttpServletResponse response){
        return servicesGuide.addPersonel(file, data, response);
    }

    @PostMapping("/any/getpersonel")
    public Personel getPersonel(@RequestBody MajorQuery majorQuery){
        return servicesGuide.getPersonel(majorQuery);
    }

    @PostMapping("/deletepersonel")
    public void deletepersonel(@RequestBody MajorQuery majorQuery, HttpServletResponse response){
        servicesGuide.removePersonel(majorQuery, response);
    }

    @PostMapping("/any/addhistory")
    public History addHistory(@RequestBody History history){
        return servicesGuide.addHistory(history);
    }

    @PostMapping("/gethistory")
    public List<History> getHistory(@RequestBody MajorQuery majorQuery){
        return servicesGuide.getHistory(majorQuery);
    }

}
