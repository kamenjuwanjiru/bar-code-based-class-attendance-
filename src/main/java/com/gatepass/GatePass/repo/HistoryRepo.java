package com.gatepass.GatePass.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gatepass.GatePass.entities.History;

public interface HistoryRepo extends JpaRepository<History, Integer>{
}
