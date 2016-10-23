package ru.mail.park.controllers.api.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by admin on 11.10.16.
 */
public class ResultJson<V> {
    public Result<V> result;

    public ResultJson(int code, V type) {
        this.result = new Result<V>(code, type);
    }

    public String getStringResult() {

        String json = null;

        ObjectMapper mapper = new ObjectMapper();

        try {
            json = mapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }
}
