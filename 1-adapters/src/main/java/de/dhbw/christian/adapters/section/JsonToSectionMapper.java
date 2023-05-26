package de.dhbw.christian.adapters.section;

import com.google.gson.Gson;
import de.dhbw.christian.domain.section.Section;

public class JsonToSectionMapper {
    public static Section map(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Section.class);
    }
}
