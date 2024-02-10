package com.gatepass.GatePass.utilities;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.json.JsonMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MyMapper {
    JsonMapper jsonMapper = new JsonMapper();

    public <T> T StringToJson(String data, Class<T> classValue){
        T t = null;

        try{
            t = jsonMapper.readValue(data, classValue);
        }catch(Exception e){
            log.warn(e.getLocalizedMessage());
        }
        return t;
    }
}
