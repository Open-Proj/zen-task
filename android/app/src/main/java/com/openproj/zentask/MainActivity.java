package com.openproj.zentask;

import android.os.Bundle;

import com.openproj.zentask.binary.BinaryCall;

import io.flutter.app.FlutterActivity;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    GeneratedPluginRegistrant.registerWith(this);

    // TODO This is ugly
    BinaryCall.installBinary(getApplicationContext());
    BinaryCall.callBinary(getApplicationContext());
  }
}
