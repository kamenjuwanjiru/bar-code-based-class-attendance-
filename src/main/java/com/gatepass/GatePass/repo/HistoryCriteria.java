package com.gatepass.GatePass.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gatepass.GatePass.dtos.MajorQuery;
import com.gatepass.GatePass.entities.History;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class HistoryCriteria {
    @Autowired
    EntityManager em;
    public java.util.List<History> getHistory(MajorQuery majorQuery){
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<History> query = criteriaBuilder.createQuery(History.class);
        Root<History> root = query.from(History.class);
        List<Predicate> predicates = new ArrayList<>();
        
        if(majorQuery.getDate() != null){
            log.info("queried with date");
            Predicate datePredicate = criteriaBuilder.like( root.get("date"), "%"+majorQuery.getDate()+"%");
            predicates.add(datePredicate);
        }
        if(majorQuery.getUnitCode() != null){
            log.info("queried with unit code");
            Predicate unitCodePredicate = criteriaBuilder.like( root.get("unitCode"), "%"+majorQuery.getUnitCode()+"%");
            predicates.add(unitCodePredicate);
        }
        if(majorQuery.getUid() != null ){
            log.info("queried with uid");
            Predicate uidPredicate = criteriaBuilder.like( root.get("uid"), "%"+majorQuery.getUid()+"%");
            predicates.add(uidPredicate);
        }

        if(majorQuery.getStaffNo() != null){
            log.info("queried with staff no");
            Predicate staffNoPredicate = criteriaBuilder.like(root.get("staffNo"), "%"+majorQuery.getStaffNo()+"%");
            predicates.add(staffNoPredicate);
        }
        //select * from history where ""= ? and "" = ?......
        Predicate finalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        query.where(finalPredicate);

        TypedQuery<History> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }
}
