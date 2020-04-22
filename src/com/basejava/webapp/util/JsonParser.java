package com.basejava.webapp.util;

import com.basejava.webapp.model.Section;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.io.Writer;

public class JsonParser {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Section.class, new JsonSectionAdapter<>())
            .create();

    public static <T> T read(Reader reader, Class<T> tClass) {
        return GSON.fromJson(reader, tClass);
    }

    public static <T> void write(T object, Writer writer) {
        GSON.toJson(object, writer);
    }
}
