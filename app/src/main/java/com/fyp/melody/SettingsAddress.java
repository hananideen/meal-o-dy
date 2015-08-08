package com.fyp.melody;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Hananideen on 8/7/2015.
 */
public class SettingsAddress extends ActionBarActivity {

    private Button buttonMain, buttonDelivery;

    SharedPreferences settings;
    SharedPreferences.Editor editor;

    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_address);

        settings = getSharedPreferences(ApplicationLoader.Settings_PREFS_NAME, 0);
        editor = settings.edit();

        buttonMain = (Button) findViewById(R.id.buttonMain);
        buttonMain.setText(settings.getString("Home", "") + ", " + settings.getString("Street", ""));
        buttonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsAddress.this, SettingsAddress1.class);
                startActivity(intent);
            }
        });

        buttonDelivery = (Button) findViewById(R.id.buttonDelivery);
        buttonDelivery.setText(settings.getString("Home2", "") + ", " + settings.getString("Street2", ""));
        buttonDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsAddress.this, SettingsAddress2.class);
                startActivity(intent);
            }
        });

    }
}
