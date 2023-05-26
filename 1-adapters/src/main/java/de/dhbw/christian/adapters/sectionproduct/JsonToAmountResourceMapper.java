package de.dhbw.christian.adapters.sectionproduct;

import com.google.gson.Gson;

public class JsonToAmountResourceMapper {
    public static AmountResource map(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, AmountResource.class);
    }
}
