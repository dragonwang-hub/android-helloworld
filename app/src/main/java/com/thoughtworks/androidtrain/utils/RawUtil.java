package com.thoughtworks.androidtrain.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import androidx.annotation.RawRes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class RawUtil {

    private static final String TAG = "RawUtil";
    public static String readFileToString(Context context, @RawRes int rawId) {
        InputStream inputStream = null;
        try {
            Resources res = context.getResources();
            inputStream = res.openRawResource(rawId);
            byte[] bytes = new byte[inputStream.available()];
            Log.d(TAG, new String(bytes));
            // read the stream to byte[]
            inputStream.read(bytes);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }
}
