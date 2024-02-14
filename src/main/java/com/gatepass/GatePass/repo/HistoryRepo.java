package com.gatepass.GatePass.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gatepass.GatePass.entities.History;

public interface HistoryRepo extends JpaRepository<History, Integer>{
    @Query("FROM History h WHERE h.date = ?1 AND h.idNo=?2")
    public History findByDateAndIdNo(String date, String idNo);
}
