package com.yumin.mp3encoder;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class ShaderLoader {

    public static String readTextFileFromRawResource(Context context,int resId) {

        InputStream inputStream = null;
        Reader reader = null;
        BufferedReader bf = null;
        StringBuilder builder = new StringBuilder();
        try {
            inputStream = context.getResources().openRawResource(resId);
            reader = new InputStreamReader(inputStream);
            bf = new BufferedReader(reader);
            String line;
            while ((line = bf.readLine())!=null) {
                builder.append(line);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (bf!=null) bf.close();
                if (reader!=null) reader.close();
                if (inputStream!=null) inputStream.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return builder.toString();
    }
}
