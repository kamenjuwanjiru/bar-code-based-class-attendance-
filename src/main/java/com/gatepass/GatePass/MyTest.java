package com.gatepass.GatePass;

import com.gatepass.GatePass.repo.HistoryRepo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class MyTest {
    @Autowired
    HistoryRepo historyRepo;


    @Test
    void testSaveHistory(){
        log.info("hello test");
    Assertions.assertEquals(null,historyRepo.findByDateAndIdNo("14-02-2024","1515536545912"), "Results should be null");
    }
}
