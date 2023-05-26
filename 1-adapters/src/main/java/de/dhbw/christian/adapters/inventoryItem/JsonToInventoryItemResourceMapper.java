package de.dhbw.christian.adapters.inventoryItem;

import com.google.gson.Gson;

public class JsonToInventoryItemResourceMapper {
    public static InventoryItemResource map(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, InventoryItemResource.class);
    }
}
