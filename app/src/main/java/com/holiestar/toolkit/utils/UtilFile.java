package com.holiestar.toolkit.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by user on 5/1/2015.
 */
public class UtilFile {
    private static final String TAG = "UtilFile";
    private static final int DEFAULT_IMAGE_COMPRESS_QUALITY = 95;

    public static long getFolderSize(String filePath) {
        if (filePath == null) {
            Log.e(TAG, "getFolderSize\tfilePath is null");
            return 0;
        }
        return getFolderSize(new File(filePath));
    }

    public static long getFolderSize(File folder) {
        if (folder == null) {
            Log.e(TAG, "getFolderSize\tfolder is null");
            return 0;
        }
        long length = 0;
        for (File file : folder.listFiles()) {
            if (file.isFile()) {
                Log.e(TAG, "getFolderSize\tfile：" + file.getName());
                Log.i("file", file.getName());
                length += file.length();
            } else {
                length += getFolderSize(file);
            }
        }
        return length;
    }

    public static void createFolder(String filePath) {
        if (filePath == null) {
            Log.e(TAG, "createFolder\tfilePath is null");
            return;
        }
        File folder = new File(filePath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public static void deleteFolder(String filePath) {
        if (filePath == null) {
            Log.e(TAG, "deleteFolder\tfilePath is null");
            return;
        }
        try {
            File file = new File(filePath);
            String[] myFiles;
            myFiles = file.list();
            for (int i = 0; i < myFiles.length; i++) {
                try {
                    File myFile = new File(file, myFiles[i]);
                    myFile.delete();
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
        }
    }

    public static void deleteFolderAsync(final String filePath) {
        if (filePath == null) {
            Log.e(TAG, "deleteFolderAsync\tfilePath is null");
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                deleteFolder(filePath);
            }
        }).start();
    }

    public static boolean isExist(String filePath) {
        if (filePath == null) {
            Log.e(TAG, "isExist\tfilePath is null");
            return false;
        }
        File file = new File(filePath);
        if (file.exists()) {
            if (file.length() == 0) {
                file.delete();
                return false;
            }
            return true;
        }
        return false;
    }

    public static void delete(String filePath) {
        if (filePath == null) {
            Log.e(TAG, "delete\tfilePath is null");
            return;
        }
        if (isExist(filePath)) {
            File file = new File(filePath);
            file.delete();
        }
    }

    public static void deleteAsync(final String filePath) {
        if (filePath == null) {
            Log.e(TAG, "deleteAsync\tfilePath is null");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                delete(filePath);
            }
        }).start();
    }

    public static String read(String filePath) {
        if (filePath == null) {
            Log.e(TAG, "read\tfilePath is null");
            return null;
        }
        try {
            StringBuffer fileData = new StringBuffer();
            BufferedReader reader = new BufferedReader(
                    new FileReader(filePath));
            char[] buf = new char[1024];
            int numRead = 0;
            while ((numRead = reader.read(buf)) != -1) {
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
            }
            reader.close();
            return fileData.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void write(String filePath, String message) {
        if (filePath == null) {
            Log.e(TAG, "write\tfilePath is null");
            return;
        }
        if (message == null) {
            Log.e(TAG, "write\tmessage is null");
            return;
        }
        try {
            FileOutputStream output = new FileOutputStream(filePath);
            output.write(message.getBytes());
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void write(final byte[] bytes, final String filePath) {
        if (filePath == null) {
            Log.e(TAG, "write\tfilePath is null");
            return;
        }
        if (bytes == null) {
            Log.e(TAG, "write\tbytes is null");
            return;
        }
        int count;
        try {
            InputStream input = new ByteArrayInputStream(bytes);
            File file = new File(filePath);
            BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file));
            byte data[] = new byte[1024];
            while ((count = input.read(data)) != -1) {
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(Bitmap bitmap, String filePath) {
        write(bitmap, Bitmap.CompressFormat.PNG, DEFAULT_IMAGE_COMPRESS_QUALITY, filePath);
    }

    public static void write(Bitmap bitmap, Bitmap.CompressFormat compressFormat, String filePath) {
        write(bitmap, compressFormat, DEFAULT_IMAGE_COMPRESS_QUALITY, filePath);
    }

    public static void write(Bitmap bitmap, Bitmap.CompressFormat compressFormat, int quality, String filePath) {
        write(bitmap, compressFormat, quality, filePath, true);
    }

    public static void write(Bitmap bitmap, Bitmap.CompressFormat compressFormat, int quality, String filePath, boolean overwrite) {
        boolean hasBitmap = bitmap != null && !bitmap.isRecycled();
        boolean hasFormat = compressFormat != null;
        boolean hasFilePath = filePath != null;
        if (!hasBitmap || !hasFormat || !hasFilePath) {
            Log.e(TAG, "write\thasBitmap:" + hasBitmap);
            Log.e(TAG, "write\thasFormat:" + hasFormat);
            Log.e(TAG, "write\thasFilePath:" + hasFilePath);
        }
        boolean hasFile = isExist(filePath);
        try {
            if (!overwrite && hasFile) {
                return;
            }

            FileOutputStream localFileOutputStream = new FileOutputStream(filePath);
            bitmap.compress(compressFormat, quality, localFileOutputStream);
            localFileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void write(View view, String filePath) {
        write(view, filePath, Bitmap.CompressFormat.PNG, DEFAULT_IMAGE_COMPRESS_QUALITY);
    }

    public static void write(View view, String filePath, final Bitmap.CompressFormat compressFormat) {
        write(view, filePath, compressFormat, DEFAULT_IMAGE_COMPRESS_QUALITY);
    }

    public static void write(View view, String filePath, final Bitmap.CompressFormat compressFormat, final int quality) {
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        write(bitmap, compressFormat, quality, filePath);
        view.setDrawingCacheEnabled(false);
    }

    public static void writeAsync(final String filePath, final String message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                write(filePath, message);
            }
        }).start();
    }

    public static void writeAsync(final byte[] bytes, final String filePath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                write(bytes, filePath);
            }
        }).start();

    }

    public static void writeAsync(final Bitmap bitmap, final String filePath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                write(bitmap, Bitmap.CompressFormat.PNG, DEFAULT_IMAGE_COMPRESS_QUALITY, filePath);
            }
        }).start();
    }

    public static void writeAsync(final Bitmap bitmap, final Bitmap.CompressFormat compressFormat, final String filePath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                write(bitmap, compressFormat, DEFAULT_IMAGE_COMPRESS_QUALITY, filePath);
            }
        }).start();

    }

    public static void writeAsync(final Bitmap bitmap, final Bitmap.CompressFormat compressFormat, final int quality, final String filePath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                write(bitmap, compressFormat, quality, filePath, true);
            }
        }).start();

    }

    public static void writeAsync(final Bitmap bitmap, final Bitmap.CompressFormat compressFormat, final int quality, final String filePath, final boolean overwrite) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                write(bitmap, compressFormat, quality, filePath, overwrite);
            }
        }).start();
    }

    public static void writeAsync(final View view, final String filePath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                write(view, filePath, Bitmap.CompressFormat.PNG, DEFAULT_IMAGE_COMPRESS_QUALITY);
            }
        }).start();

    }

    public static void writeAsync(final View view,final String filePath, final Bitmap.CompressFormat compressFormat) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                write(view, filePath, compressFormat, DEFAULT_IMAGE_COMPRESS_QUALITY);
            }
        }).start();

    }

    public static void writeAsync(final View view, final String filePath, final Bitmap.CompressFormat compressFormat, final int quality) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                write(view, filePath, compressFormat, quality);
            }
        }).start();
    }

    public static void openFile(Context context, String filePath, String errorMessage)
    {
        try {
            File file = new File(filePath);
            MimeTypeMap map = MimeTypeMap.getSingleton();
            String ext = MimeTypeMap.getFileExtensionFromUrl(file.getName());
            String type = map.getMimeTypeFromExtension(ext);

            if (type == null){
                type = "*/*";
            }

            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri data = Uri.fromFile(file);
            intent.setDataAndType(data, type);
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context,errorMessage, Toast.LENGTH_SHORT).show();
        }
    }
}
