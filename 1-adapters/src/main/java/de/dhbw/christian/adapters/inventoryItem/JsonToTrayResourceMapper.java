package de.dhbw.christian.adapters.inventoryItem;

import com.google.gson.Gson;

public class JsonToTrayResourceMapper {
    public static TrayResouce map(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, TrayResouce.class);
    }
}
