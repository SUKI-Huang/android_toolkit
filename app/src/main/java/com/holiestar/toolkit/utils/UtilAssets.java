package com.holiestar.toolkit.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by tony1 on 2/14/2017.
 */

public class UtilAssets {
    private static Context context;

    public static void initialize(Context _context) {
        context = _context;
    }

    public static void copyToFileAsync(final String assetsPath,final String filePath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                copyToFile(assetsPath, filePath);
            }
        }).start();
    }

    public static void copyToFile(String assetsPath, String filePath) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            AssetManager am = context.getAssets();
            inputStream = am.open(assetsPath);
            File file=new File(filePath);
            outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (inputStream != null)
                    inputStream.close();
                if (outputStream != null)
                    outputStream.close();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
            return;
        }
        return;
    }

    public static String read(String assetsPath) {
        String tContents = "";
        try {
            InputStream stream = context.getAssets().open(assetsPath);

            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);
        } catch (IOException e) {
            // Handle exceptions here
        }

        return tContents;
    }
}
