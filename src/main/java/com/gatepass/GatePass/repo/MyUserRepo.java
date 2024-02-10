package com.gatepass.GatePass.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gatepass.GatePass.entities.MyUser;

public interface MyUserRepo extends JpaRepository<MyUser, Integer>{
    public MyUser findByUsername(String username);
}
