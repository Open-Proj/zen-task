package com.openproj.zentask;

import android.os.Bundle;
import android.util.Log;

import com.openproj.zentask.binary.NativeBinary;

import java.io.IOException;

import io.flutter.app.FlutterActivity;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {
  private static String TAG = "main";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    GeneratedPluginRegistrant.registerWith(this);

    NativeBinary bin = new NativeBinary(getApplicationContext());
    if (!bin.isInstalled()) {
      try {
        bin.install();
      } catch (IOException e) {
        Log.e(TAG, "Error installing binary.");
        e.printStackTrace();
      }
    }

    try {
      bin.call();
    } catch (IOException e) {
      Log.e(TAG, "Error calling binary.");
      e.printStackTrace();
    }
  }
}
