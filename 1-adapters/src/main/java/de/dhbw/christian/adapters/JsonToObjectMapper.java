package de.dhbw.christian.adapters;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class JsonToObjectMapper {

    public static <T> T map(Class<T> tClass, String json) throws JsonSyntaxException {
        Gson gson = new Gson();
        return gson.fromJson(json, tClass);
    }
}
