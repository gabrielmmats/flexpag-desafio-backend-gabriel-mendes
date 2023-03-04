package com.flexpag.paymentscheduler.utils;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class APIResponse {

    @Getter
    Map <String, Object> data;

    public APIResponse(String key, Object value){
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        this.data = map;
    }

}
