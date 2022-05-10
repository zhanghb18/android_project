package com.example.forum.bean;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @description: request 和 response 的快速生成和解析
 */

public class GsonFunction {
    static final Gson gson = new Gson();
    /**
     *
     * @param string: String
     * @param Class: T.class
     * @param <T> T
     * @return T
     */
    public static <T> T parseToObject(String string, Object Class) {
        return gson.fromJson(string, (Type) Class);
    }

    public static List<String> parseToList(String string) {
        Type type = new TypeToken<List<String>>(){}.getType();
        return gson.fromJson(string, type);
    }

    public static JSONObject parseToJsonObject(String string) throws JSONException {
        return new JSONObject(string);
    }

    public static String parseToString(JSONObject info) {
        return info.toString();
    }
}
