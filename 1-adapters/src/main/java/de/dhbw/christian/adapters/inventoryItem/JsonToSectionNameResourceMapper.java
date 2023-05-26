package de.dhbw.christian.adapters.inventoryItem;

import com.google.gson.Gson;

public class JsonToSectionNameResourceMapper {
    public static SectionNameResource map(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, SectionNameResource.class);
    }
}
