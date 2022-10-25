


package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class SettingsActivity extends AppCompatActivity {

    public boolean soundAble;
    public boolean vibrationSensor;
    public boolean lightSensor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
//        if (savedInstanceState == null) {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.settings, new SettingsFragment())
//                    .commit();
//        }
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }

        //光感开关检查
        Switch lightS = findViewById(R.id.light);
        lightS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                lightSensor = isChecked;
                Intent intent = new Intent();
                intent.putExtra("lightSensor", lightSensor);
                intent.putExtra("vibrationSensor", vibrationSensor);
                intent.putExtra("soundAble", soundAble);
                setResult(0, intent);
                Log.i("TEST", "Testing  Send Feedback");
            }
        });

        //声感开关检查
        Switch soundS = findViewById(R.id.sound);
        soundS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                soundAble = isChecked;
                Log.i("TEST", "Testing the sound now is " + Boolean.toString(soundAble));
                Intent intent = new Intent();
                intent.putExtra("lightSensor", lightSensor);
                intent.putExtra("vibrationSensor", vibrationSensor);
                intent.putExtra("soundAble", soundAble);
                setResult(0, intent);
                Log.i("TEST", "Testing  Send Feedback");
            }
        });

        //声感开关检查
        Switch vibrations = findViewById(R.id.vibration);
        vibrations.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                vibrationSensor = isChecked;
                Intent intent = new Intent();
                intent.putExtra("lightSensor", lightSensor);
                intent.putExtra("vibrationSensor", vibrationSensor);
                intent.putExtra("soundAble", soundAble);
                setResult(0, intent);
                Log.i("TEST", "Testing  Send Feedback");
            }
        });
//        receive data from main activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            soundAble = extras.getBoolean("soundAble");
            vibrationSensor = extras.getBoolean("vibrationSensor");
            lightSensor = extras.getBoolean("lightSensor");
            Log.i("TEST", "Testing" + Boolean.toString(soundAble));
            lightS.setChecked(lightSensor);
            soundS.setChecked(soundAble);
            vibrations.setChecked(vibrationSensor);

        }


        // 返回按钮
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v ->
                {
                    finish();
                }
        );
    }

}