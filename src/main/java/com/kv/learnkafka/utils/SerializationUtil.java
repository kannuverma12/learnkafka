package com.kv.learnkafka.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unchecked")
public class SerializationUtil {
    private static final Logger logger = LoggerFactory
            .getLogger(SerializationUtil.class);

    private static Gson gson = new Gson();
    private static ObjectMapper mapper = new ObjectMapper ();

    public static String toJson(Object data) throws JsonProcessingException {
        return mapper.writeValueAsString(data);
    }

    public static <T>  T mapToTypedObject(Map<String,Object> data, Class<T> type) {
        try {
            return mapper.readValue(mapper.writeValueAsString(data), type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static Object mapToObject(Map<String,Object> data, Class type)
            throws IOException {
        return mapper.readValue(mapper.writeValueAsString(data), type);
    }


    public static <T> T jsonToObject(String data, Class<T> type)
            throws IOException{
        return mapper.readValue(data, type);
    }

    public static Map<String, Object> getMapOfJson(String json) {
        return gson.fromJson(json,
                new TypeToken<HashMap<String, Object>>() {}.getType());
    }

    public static Map<String, Object> getMapOfString(String str)
            throws IOException{
        return str == null ? null :mapper.readValue(str, Map.class);
    }

    @SuppressWarnings("unchecked")
    public static Map<String,Object> getMapOfFile(String fileName) {
        try{
            return gson.fromJson(new FileReader(fileName),
                    new TypeToken<HashMap<String, Object>>() {}.getType());
        }catch (FileNotFoundException fileNotFoundException){
            logger.info(Arrays.asList(fileNotFoundException.getStackTrace()).toString());
        }
        return Collections.EMPTY_MAP;

    }

    public static Map<String,Object> getMapOfStream(InputStream inputStream) {
            return gson.fromJson(new InputStreamReader(inputStream),
                    new TypeToken<HashMap<String, Object>>() {}.getType());
    }


    public static Map<String,Object> getMapOfObject(Object object)
            throws JsonProcessingException {
        Map<String,Object> mapOfObject = gson.fromJson(mapper.writeValueAsString(object),
                new TypeToken<HashMap<String, Object>>() {
                }.getType());
        replaceNullWithBlank(mapOfObject);
        return mapOfObject;
    }

    public static Map<String,Object> getMapOfObject(Object object, boolean replaceNullWithBlank)
            throws JsonProcessingException {
        Map<String,Object> mapOfObject = gson.fromJson(mapper.writeValueAsString(object),
                new TypeToken<HashMap<String, Object>>() {
                }.getType());
        if(replaceNullWithBlank) replaceNullWithBlank(mapOfObject);
        return mapOfObject;
    }


    @SuppressWarnings("unchecked")
    public static void replaceNullWithBlank(Map<String,Object> data){
        replaceNullWithStr(data, Constants.BLANK);
    }

    public static void replaceNullWithStr(Map<String,Object> data, String str){
        for(Map.Entry<String,Object> entry : data.entrySet()){
            if(entry.getValue() == null){
                entry.setValue(null);
            }
            else if(entry.getValue() instanceof Collection){
                for(Object val : (Collection)entry.getValue()){
                    if(val instanceof Map){
                        replaceNullWithStr((Map<String,Object>)val, str);
                    }
                }
            }
            else if(entry.getValue() instanceof Map){
                replaceNullWithStr((Map<String,Object>)entry.getValue(), str);
            }
        }
    }

    public static void replaceStrWithNull(Map<String,Object> data, String str){
        for(Map.Entry<String,Object> entry : data.entrySet()){
            if(entry.getValue().equals(str)){
                entry.setValue(null);
            }
            else if(entry.getValue() instanceof Collection){
                for(Object val : (Collection)entry.getValue()){
                    if(val instanceof Map){
                        replaceStrWithNull((Map<String,Object>)val, str);
                    }
                }
            }
            else if(entry.getValue() instanceof Map){
                replaceStrWithNull((Map<String,Object>)entry.getValue(), str);
            }
        }
    }

}
