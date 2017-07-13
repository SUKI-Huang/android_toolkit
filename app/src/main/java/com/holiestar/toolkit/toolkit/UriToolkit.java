package com.holiestar.toolkit.toolkit;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;

/**
 * Created by tony1 on 1/21/2017.
 */

public class UriToolkit {
    private static Context context;
    private static boolean isKitKat;

    public static void initialize(Context _context) {
        context = _context.getApplicationContext();
        isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static String getTitleFromMediaStore(Uri uri) {
        Object o = getRankColumn(
                uri,
                MediaStore.MediaColumns.TITLE,
                MediaStore.MediaColumns.DISPLAY_NAME
        );
        return (o != null && o instanceof String) ? (String) o : null;
    }

    public static Object getRankColumn(final Uri uri, String... columns) {
        boolean hasColumns = columns != null && columns.length > 0;
        if (columns == null) return null;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getRankDataColumn(context, contentUri, columns, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getRankDataColumn(context, contentUri, columns, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getRankDataColumn(context, uri, columns, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static Object getRankDataColumn(Context context, Uri uri, String[] columns, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, columns, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                for (String columnName : columns) {
                    Object object = null;
                    int fieldType = cursor.getType(cursor.getColumnIndexOrThrow(columnName));
                    switch (fieldType) {
                        case Cursor.FIELD_TYPE_BLOB:
                            object = cursor.getBlob(cursor.getColumnIndexOrThrow(columnName));
                            break;
                        case Cursor.FIELD_TYPE_FLOAT:
                            object = cursor.getFloat(cursor.getColumnIndexOrThrow(columnName));
                            break;
                        case Cursor.FIELD_TYPE_INTEGER:
                            object = cursor.getInt(cursor.getColumnIndexOrThrow(columnName));
                            break;
                        case Cursor.FIELD_TYPE_STRING:
                            object = cursor.getString(cursor.getColumnIndexOrThrow(columnName));
                            break;
                    }
                    if (object != null) {
                        cursor.close();
                        return object;
                    }
                }
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    //MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    public static Uri getUriFromFilePath(Uri uriType, String filePath) {
        try {
            Cursor cursor = context.getContentResolver().query(
                    uriType,
                    new String[]{MediaStore.Audio.Media._ID},
                    MediaStore.Audio.Media.DATA + "=? ",
                    new String[]{filePath}, null);
            if (cursor != null && cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
                cursor.close();
                return Uri.withAppendedPath(uriType, "" + id);
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static class Ringtone {

        public static Uri getDefault() {
            return RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        }

        public static String getName(Uri uri) {
            try {
                return RingtoneManager.getRingtone(context, uri).getTitle(context);
            } catch (Exception e) {
                return null;
            }
        }
    }
}
