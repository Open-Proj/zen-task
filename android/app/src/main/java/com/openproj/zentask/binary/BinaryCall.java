package com.openproj.zentask.binary;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Helper class with utilities for calling native binaries.
 * TODO Make this look like real Java code
 */
public class BinaryCall {
    private static String TAG = "binary";

    private static String EXECUTABLE_NAME = "sample_arm_bin";

    /** Executes the binary. */
    public static void callBinary(Context context) {
        Log.d(TAG, "Attempting to execute binary.");

        try {
            Process process = Runtime.getRuntime().exec(getExecutablePath(context));
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line = reader.readLine();
            Log.i(TAG, "Binary output: " + line);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Could not execute binary!");
        }
    }

    public static void installBinary(Context context) {
        Log.d(TAG, "App data path: " + context.getFilesDir());
        File file = new File(context.getFilesDir().getPath() + EXECUTABLE_NAME);
        if (!file.exists()) {
            Log.d(TAG, "Copying binary.");
            copyAssets(context);
        }
    }

    /** Copies the executable file from the `assets` folder to the `data` directory to make it
     * executable. */
    private static void copyAssets(Context context) {
        AssetManager assets = context.getAssets();

        try {
            File out = new File(getExecutablePath(context));
            copyFile(assets.open(EXECUTABLE_NAME), out);
            out.setExecutable(true);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Error copying executable file.");
        }
    }

    /** Copies a file. */
    private static void copyFile(InputStream src, File dst) throws IOException {
        try (OutputStream out = new FileOutputStream(dst)) {
            byte[] buf = new byte[1024];
            int len;
            while ((len = src.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        }
    }

    private static String getExecutablePath(Context context) {
        return context.getFilesDir().getPath() + "/" + EXECUTABLE_NAME;
    }
}