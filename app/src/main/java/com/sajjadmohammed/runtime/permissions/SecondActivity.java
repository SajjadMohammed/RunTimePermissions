package com.sajjadmohammed.runtime.permissions;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.sajjadmohammed.runtime.permissions.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

    ActivitySecondBinding secondBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        secondBinding = DataBindingUtil.setContentView(this, R.layout.activity_second);
        //
        secondBinding.finish.setOnClickListener(v -> {
            Intent intent = getIntent();
            intent.putExtra("someData", secondBinding.someData.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}