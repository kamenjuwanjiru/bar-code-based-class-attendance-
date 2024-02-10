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

@Repository
public class HistoryCriteria {
    @Autowired
    EntityManager em;
    public java.util.List<History> getHistory(MajorQuery majorQuery){
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<History> query = criteriaBuilder.createQuery(History.class);
        Root<History> root = query.from(History.class);
        List<Predicate> predicates = new ArrayList<>();
        
        if(majorQuery.getDate() != null){
            Predicate datePredicate = criteriaBuilder.like( root.get("date"), "%"+majorQuery.getDate()+"%");
            predicates.add(datePredicate);
        }
        if(majorQuery.getEmail() != null){
            Predicate emailPredicate = criteriaBuilder.like( root.get("email"), "%"+majorQuery.getEmail()+"%");
            predicates.add(emailPredicate);
        }
        if(majorQuery.getUid() != null ){
            Predicate uidPredicate = criteriaBuilder.like( root.get("uid"), "%"+majorQuery.getUid()+"%");
            predicates.add(uidPredicate);
        }
        //select * from history where ""= ? and "" = ?......
        Predicate finalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        query.where(finalPredicate);

        TypedQuery<History> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }
}
