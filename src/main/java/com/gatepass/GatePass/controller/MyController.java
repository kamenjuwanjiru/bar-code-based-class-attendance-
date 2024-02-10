package com.gatepass.GatePass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gatepass.GatePass.barqrgenerator.Generator;
import com.gatepass.GatePass.dtos.StoredData;


@RestController
public class MyController {
    @Autowired
    Generator generator;

    @PostMapping("/")
    public boolean generateBarAndQrcode(@RequestBody StoredData storedData)throws Exception{
        return generator.generateBarAndQrcode(storedData);
    }

}
