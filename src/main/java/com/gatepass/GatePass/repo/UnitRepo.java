package com.gatepass.GatePass.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gatepass.GatePass.entities.Unit;

public interface UnitRepo extends JpaRepository<Unit, Integer>{
    public Unit findByUnitCode(String unitCode);
    public List<Unit> findAllByStaffNo(String staffNo);
}
