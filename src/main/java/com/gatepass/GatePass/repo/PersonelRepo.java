package com.gatepass.GatePass.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gatepass.GatePass.entities.Personel;

public interface PersonelRepo extends JpaRepository<Personel, Integer>{
    public Personel findByUid(String uid);
    public Personel findByIdNo(String idNo);
}
