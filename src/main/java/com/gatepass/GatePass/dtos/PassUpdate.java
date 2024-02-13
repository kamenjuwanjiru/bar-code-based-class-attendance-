package com.gatepass.GatePass.dtos;

import lombok.Data;

@Data
public class PassUpdate {
    private String oldPassword;
    private String newPassword;
}
