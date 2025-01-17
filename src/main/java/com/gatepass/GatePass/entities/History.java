package com.gatepass.GatePass.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String uid;
    private String email;
    private String fullName;
    private String date;
    private String timeStamp;
    private String unitCode;
    private String staffNo;
    private String idNo;
}
