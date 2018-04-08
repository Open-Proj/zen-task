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

/** Represents a native binary that can be called and have its output parsed. */
public class NativeBinary {
    private static String TAG = "binary";

    private static String EXECUTABLE_NAME = "sample_arm_bin";

    private Context context;
    private String executablePath;

    public NativeBinary(Context context) {
        this.context = context;

        this.executablePath = context.getFilesDir().getPath() +
                File.separator +
                EXECUTABLE_NAME;
    }

    /** Executes the binary. */
    public void call() throws IOException {
        Log.d(TAG, "Executing binary located at '" + executablePath + "'.");

        Process process = Runtime.getRuntime().exec(executablePath);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream())
        );

        String line = reader.readLine();

        Log.i(TAG, "Binary output: " + line);
    }

    /**
     * Copies the executable file from the `assets` folder to the `data` directory to make it
     * executable.
     */
    public void install() throws IOException {
        AssetManager assets = context.getAssets();

        try {
            Log.d(TAG, "Copying binary from assets to '" + executablePath + "'.");

            InputStream src = assets.open(EXECUTABLE_NAME);
            File dst = new File(executablePath);

            // Copy file from assets to data directory.
            try (OutputStream out = new FileOutputStream(dst)) {
                byte[] buf = new byte[1024];
                int len;
                while ((len = src.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }

            dst.setExecutable(true);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Error copying executable file.");
        }
    }

    public boolean isInstalled() {
        return new File(executablePath).exists();
    }
}