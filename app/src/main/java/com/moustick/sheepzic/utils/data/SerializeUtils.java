package com.moustick.sheepzic.utils.data;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SerializeUtils {

    public static String serialize(@Nullable ArrayList list) {
        Gson gson = new Gson();
        if (list != null) {
            return gson.toJson(list);
        } else {
            return gson.toJson(new ArrayList());
        }
    }

    public static ArrayList<String> deserializeList(@Nullable String serializedList) {
        Gson gson = new Gson();
        if (serializedList == null || serializedList.isEmpty()) {
            return new ArrayList<>();
        } else {
            Type type = new TypeToken<List<String>>() {
            }.getType();
            return gson.fromJson(serializedList, type);
        }
    }

}
