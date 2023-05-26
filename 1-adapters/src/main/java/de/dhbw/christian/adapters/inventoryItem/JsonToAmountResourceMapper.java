package de.dhbw.christian.adapters.inventoryItem;

import com.google.gson.Gson;
import de.dhbw.christian.domain.section.Section;

public class JsonToAmountResourceMapper {
    public static AmountResource map(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, AmountResource.class);
    }
}
