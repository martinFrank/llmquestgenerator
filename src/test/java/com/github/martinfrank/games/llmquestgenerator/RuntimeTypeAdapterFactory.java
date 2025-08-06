package com.github.martinfrank.games.llmquestgenerator;

import com.google.gson.*;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class RuntimeTypeAdapterFactory<T> implements TypeAdapterFactory {

    private final Class<?> baseType;
    private final String typeFieldName;
    private final Map<String, Class<?>> labelToSubtype = new LinkedHashMap<>();

    private RuntimeTypeAdapterFactory(Class<?> baseType, String typeFieldName) {
        this.baseType = baseType;
        this.typeFieldName = typeFieldName;
    }

    public static <T> RuntimeTypeAdapterFactory<T> of(Class<T> baseType, String typeFieldName) {
        return new RuntimeTypeAdapterFactory<>(baseType, typeFieldName);
    }

    public RuntimeTypeAdapterFactory<T> registerSubtype(Class<? extends T> subtype, String label) {
        labelToSubtype.put(label, subtype);
        return this;
    }

    @Override
    public <R> TypeAdapter<R> create(Gson gson, TypeToken<R> type) {
        if (!baseType.isAssignableFrom(type.getRawType())) {
            return null;
        }

        Map<String, TypeAdapter<?>> labelToAdapter = new LinkedHashMap<>();
        Map<Class<?>, String> subtypeToLabel = new LinkedHashMap<>();

        for (Map.Entry<String, Class<?>> entry : labelToSubtype.entrySet()) {
            TypeAdapter<?> adapter = gson.getDelegateAdapter(this, TypeToken.get(entry.getValue()));
            labelToAdapter.put(entry.getKey(), adapter);
            subtypeToLabel.put(entry.getValue(), entry.getKey());
        }

        return new TypeAdapter<R>() {

            @Override
            public void write(JsonWriter out, R value) throws IOException {
                Class<?> srcType = value.getClass();
                String label = subtypeToLabel.get(srcType);
                TypeAdapter<R> adapter = (TypeAdapter<R>) labelToAdapter.get(label);

                JsonObject jsonObject = adapter.toJsonTree(value).getAsJsonObject();
                jsonObject.addProperty(typeFieldName, label);
                Streams.write(jsonObject, out);
            }

            @Override
            public R read(JsonReader in) throws IOException {
                JsonObject jsonObject = Streams.parse(in).getAsJsonObject();
                JsonElement typeElement = jsonObject.remove(typeFieldName);

                String label = typeElement.getAsString();
                TypeAdapter<R> adapter = (TypeAdapter<R>) labelToAdapter.get(label);
                return adapter.fromJsonTree(jsonObject);
            }
        };
    }
}