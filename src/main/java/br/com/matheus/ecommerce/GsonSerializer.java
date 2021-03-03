package br.com.matheus.ecommerce;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Serializer;

public class GsonSerializer<T> implements Serializer<T> {

    /* isto irá retornar um serializador */
    private final Gson gson = new GsonBuilder().create();

    /* converter nosso obj em string */
    @Override
    public byte[] serialize(String s, T object) {
        return gson.toJson(object).getBytes();
    }
}
