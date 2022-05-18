package com.sajjadmohammed.runtime.permissions;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.sajjadmohammed.runtime.permissions.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final ActivityResultLauncher<Intent> startAnActivityForResult =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getData() != null && result.getResultCode() == RESULT_OK) {
                    String someData = result.getData().getStringExtra("someData");
                    Toast.makeText(this, someData, Toast.LENGTH_LONG).show();
                }
            });
    ActivityMainBinding mainBinding;
    private final ActivityResultLauncher<String[]> multiplePermission =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                if (Boolean.TRUE.equals(result.get(PermissionManager.cameraPermission))) {
                    // capture();
                    Snackbar.make(mainBinding.openCamera, "Camera Permission Granted", Snackbar.LENGTH_LONG).show();
                }
                if (Boolean.TRUE.equals(result.get(PermissionManager.storagePermission))) {
                    // openFile();
                    Snackbar.make(mainBinding.openFile, "Storage Permission Granted", Snackbar.LENGTH_LONG).show();
                }
            });
    private final ActivityResultLauncher<String> cameraPermission =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // capture();
                    Snackbar.make(mainBinding.openCamera, "Camera Permission Granted", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(mainBinding.openCamera, "Camera Permission Denied", Snackbar.LENGTH_LONG).show();
                }
            });
    private final ActivityResultLauncher<String> storagePermission =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // openFile();
                    Snackbar.make(mainBinding.openFile, "Storage Permission Granted", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(mainBinding.openFile, "Storage Permission Denied", Snackbar.LENGTH_LONG).show();
                }
            });

    private final ActivityResultLauncher<String> getContent =
            registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
                if (result != null) {
                    String uri = result.getPath();
                    mainBinding.myImage.setImageURI(result);
                    Toast.makeText(this, uri, Toast.LENGTH_LONG).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        //

        // Camera
        mainBinding.openCamera.setOnClickListener(v -> lunchCamera());

        // Storage
        mainBinding.openFile.setOnClickListener(v -> openFile());


        // Multiple Permission
        mainBinding.multiplePermission.setOnClickListener(v -> requestMultiplePermission());

        //
        mainBinding.getContent.setOnClickListener(v -> getContent());

        //
        mainBinding.getData.setOnClickListener(v -> {
            Intent intent = new Intent(this, SecondActivity.class);
            startAnActivityForResult.launch(intent);
        });
    }

    private void openFile() {
        if (PermissionManager.shouldAskPermission()) {
            if (PermissionManager.isStoragePermissionAlreadyGranted(this)) {
                //openFile();
            } else {
                storagePermission.launch(PermissionManager.storagePermission);
            }
        } else {
            //openFile();
        }
    }

    private void lunchCamera() {
        if (PermissionManager.shouldAskPermission()) {
            if (PermissionManager.isCameraPermissionAlreadyGranted(this)) {
                //capture();
            } else {
                cameraPermission.launch(PermissionManager.cameraPermission);
            }
        } else {
            //capture();
        }
    }

    private void requestMultiplePermission() {
        if (PermissionManager.shouldAskPermission()) {
            List<String> permissions = new ArrayList<>();

            if (PermissionManager.isCameraPermissionAlreadyGranted(this)) {
                // capture();
            } else {
                permissions.add(PermissionManager.cameraPermission);
            }

            if (PermissionManager.isStoragePermissionAlreadyGranted(this)) {
                // openFile();
            } else {
                permissions.add(PermissionManager.storagePermission);
            }

            if (!permissions.isEmpty()) {
                multiplePermission.launch(permissions.toArray(new String[0]));
            }

        } else {
            //capture(); openFile();
        }
    }

    private void getContent() {
        getContent.launch("image/*");
    }
}