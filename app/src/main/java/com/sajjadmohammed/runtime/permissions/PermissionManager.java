package com.sajjadmohammed.runtime.permissions;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.content.ContextCompat;

public class PermissionManager {

    public static String storagePermission = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static String cameraPermission = Manifest.permission.CAMERA;

    public static boolean shouldAskPermission() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static boolean isStoragePermissionAlreadyGranted(Context context) {
        return ContextCompat.checkSelfPermission(context, storagePermission) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean isCameraPermissionAlreadyGranted(Context context) {
        return ContextCompat.checkSelfPermission(context, cameraPermission) == PackageManager.PERMISSION_GRANTED;
    }
}