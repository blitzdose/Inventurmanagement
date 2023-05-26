package de.dhbw.christian.adapters.sectionproduct;

import com.google.gson.Gson;

public class JsonToSectionProductResourceMapper {
    public static InputSectionProductResource map(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, InputSectionProductResource.class);
    }
}
