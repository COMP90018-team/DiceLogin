


package com.example.myapplication;
import java.util.HashMap;
import android.content.Intent;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

public class SettingsActivity extends AppCompatActivity {
    LightListener mLightListener = null;

    public boolean soundAble;
    public boolean vibrationSensor;
    public boolean lightSensor;
    private String[] cupList = new String[]{"bamboo","crystal","diamond","gold","jelly","leaves","marble","normal","ocean","starrynight"};
    public HashMap<String, Integer> cupImages = new HashMap<String, Integer>();
    public static int selectedCup = R.drawable.cup_normal;

    private void initCupMap(){
        cupImages.put("bamboo", R.drawable.cup_bamboo);
        cupImages.put("crystal",R.drawable.cup_crystal);
        cupImages.put("diamond", R.drawable.cup_diamond);
        cupImages.put("gold", R.drawable.cup_gold);
        cupImages.put("jelly", R.drawable.cup_jelly);
        cupImages.put("leaves", R.drawable.cup_leaves);
        cupImages.put("marble", R.drawable.cup_marble);
        cupImages.put("normal", R.drawable.cup_normal);
        cupImages.put("ocean", R.drawable.cup_ocean);
        cupImages.put("starrynight", R.drawable.cup_starrynight);

        Log.i("INIT_CUPMAP","SUCCEED");
    }


    private String currentCup = "normal";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        // InitCupMap
        initCupMap();

        mLightListener = new LightListener(this);
        mLightListener.setOnLightListener(new SettingsActivity.lightChangeListener());

        //光感开关检查
        Switch lightS = findViewById(R.id.light);
        lightS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mLightListener.start();
                }else{
                    mLightListener.stop();
                }
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
            if(lightSensor){
                mLightListener.start();
            }else{
                mLightListener.stop();
            }

        }


        // 返回按钮
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v ->
                {
                    finish();
                }
        );

        // Confirm Button for Setting Dice Cup texture
        Button btnConfirm = findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer objI = cupImages.get(currentCup);
                selectedCup = Integer.valueOf(objI);
                Toast.makeText(SettingsActivity.this, "New Dice Cup Set!", Toast.LENGTH_SHORT).show();
            }
        });

        // Spinner for setting dice cup
        Spinner cupSpinner = findViewById(R.id.spinner_dicecup);
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,cupList);//建立Adapter并且绑定数据源
        cupSpinner.setAdapter(adapter);
        cupSpinner.setSelection(7,true);

        cupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("SPINNER POSITION",position+"");
                currentCup = cupList[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }
    private class lightChangeListener implements LightListener.LightChangeListener {
        ConstraintLayout SettingsLayout = findViewById(R.id.shock);
        TextView lightSwitch = findViewById(R.id.light);
        TextView vibrationSwitch = findViewById(R.id.vibration);
        TextView soundSwitch = findViewById(R.id.sound);
        Button btnBack = findViewById(R.id.btn_back);
        @Override
        public void ChangeLight(SensorEvent temp) {
            float acc = temp.accuracy;
            float lux = temp.values[0];
            try {
                if (lux >= 100) {
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    SettingsLayout.setBackgroundResource(R.color.cream_000);
                    lightSwitch.setTextColor(getResources().getColor(R.color.cream_300));
                    vibrationSwitch.setTextColor(getResources().getColor(R.color.cream_300));
                    soundSwitch.setTextColor(getResources().getColor(R.color.cream_300));
//                   recreate();

                } else {
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    SettingsLayout.setBackgroundResource(R.color.blue_700);
                    lightSwitch.setTextColor(getResources().getColor(R.color.blue_100));
                    vibrationSwitch.setTextColor(getResources().getColor(R.color.blue_100));
                    soundSwitch.setTextColor(getResources().getColor(R.color.blue_100));

//                    recreate();
                }
            } catch (Exception e) {
            }

        }
    }

}