package ru.mpei.latushkina.fqw.utils;

import com.google.gson.Gson;

public class ParserJson {
    private static final ParserJson instance = new ParserJson();
    private final Gson gson;

    private ParserJson() {
        gson = new Gson();
    }

    public static ParserJson getInstance() {
        return instance;
    }

    public <T> String dataToString(T dataClass) {
        return gson.toJson(dataClass);
    }

    public <T> T parseData(String dataString, Class<T> clazz) {
        return gson.fromJson(dataString, clazz);
    }
}
