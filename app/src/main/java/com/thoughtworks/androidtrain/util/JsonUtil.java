package com.thoughtworks.androidtrain.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class JsonUtil {

    private static final String TAG = "JsonUtil";

    public static String getJson(String fileName, Context context) {
        AssetManager assetManager = context.getAssets();

        StringBuilder stringBuilder = new StringBuilder();

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(assetManager.open(fileName), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            Log.d(TAG, "File read failed, please check the file.");
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static <T> T JsonToObject(String json, Class<T> type) {
        Gson gson =new Gson();
        return gson.fromJson(json, type);
    }
}
