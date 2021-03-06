package com.fyp.melody.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.melody.ApplicationLoader;
import com.fyp.melody.map.GPSTracker;
import com.fyp.melody.R;
import com.fyp.melody.map.WorkaroundMapFragment;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by Hananideen on 4/8/2015.
 */
public class SettingsAddress2 extends AppCompatActivity {

    SharedPreferences settings;
    SharedPreferences.Editor editor;
    GoogleMap map;
    GPSTracker gps;
    ArrayList<LatLng> markerPoints;
    private EditText editHome, editStreet, editSearch;
    private Button buttonConfirm, buttonCancel, buttonSearch;
    private String home, search, savedLatitude, savedLongitude, latitude, longitude;
    private TextView lat, lng;
    private ScrollView mScrollView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_address1);

        lat = (TextView) findViewById(R.id.latitude);
        lng = (TextView) findViewById(R.id.longitude);

        settings = getSharedPreferences(ApplicationLoader.Settings_PREFS_NAME, 0);
        editor = settings.edit();

        markerPoints = new ArrayList<LatLng>();
        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        map = ((WorkaroundMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setMyLocationEnabled(true);
        ((WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).setListener(new WorkaroundMapFragment.OnTouchListener() {
            @Override
            public void onTouch() {
                mScrollView.requestDisallowInterceptTouchEvent(true);
            }
        });
        gps = new GPSTracker(SettingsAddress2.this);
        if(gps.canGetLocation()) {
            //do nothing
        } else {
            gps.showSettingsAlert();

        }

        savedLatitude = settings.getString("Latitude2", "");
        savedLongitude = settings.getString("Longitude2", "");
        lat.setText("Latitude: "+savedLatitude);
        lng.setText("Longitude: " +savedLongitude);

        if(savedLatitude.length()==0){
            //do nothing
        } else{

            Double latDouble = Double.parseDouble(savedLatitude);
            Double longDouble = Double.parseDouble(savedLongitude);

            LatLng location = new LatLng(latDouble, longDouble);
            latitude = String.format("%.6f", location.latitude);
            longitude = String.format("%.6f", location.longitude);
            map.addMarker(new MarkerOptions().position(location).title("Your location"));
            CameraUpdate zoomLocation = CameraUpdateFactory.newLatLngZoom(location, 15);
            map.animateCamera(zoomLocation);
        }

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {

                markerPoints.clear();
                map.clear();

                markerPoints.add(point);
                MarkerOptions options = new MarkerOptions();
                options.position(point);
                map.addMarker(options.title("Your location"));

                latitude = String.format("%.6f", point.latitude);
                longitude = String.format("%.6f", point.longitude);

                lat.setText("Latitude: " + latitude);
                lng.setText("Longitude: " + longitude);

            }
        });

        editSearch = (EditText) findViewById(R.id.searchPlaces);

        buttonSearch = (Button) findViewById(R.id.SearchButton);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search = editSearch.getText().toString();

                if (search.equals("MMU") || search.equals("Multimedia University") || search.equals("mmu") || search.equals("multimedia university")) {

                    LatLng mmu = new LatLng(2.927, 101.641);
                    CameraUpdate mmuLocation = CameraUpdateFactory.newLatLngZoom(mmu, 15);
                    map.animateCamera(mmuLocation);

                } else if (search.equals("TPC") || search.equals("Taman Pinggiran Cyber") || search.equals("tpc") || search.equals("taman pinggiran cyber") || search.equals("PC") || search.equals("Pinggiran Cyber") || search.equals("pc") || search.equals("pinggiran cyber")) {

                    LatLng tpc = new LatLng(2.914, 101.629);
                    CameraUpdate tpcLocation = CameraUpdateFactory.newLatLngZoom(tpc, 15);
                    map.animateCamera(tpcLocation);

                } else if (search.equals("Cyberia") || search.equals("cyberia")) {

                    LatLng cbr = new LatLng(2.923, 101.638);
                    CameraUpdate cbrLocation = CameraUpdateFactory.newLatLngZoom(cbr, 15);
                    map.animateCamera(cbrLocation);

                } else {
                    Toast.makeText(getApplicationContext(), "Location not found", Toast.LENGTH_LONG).show();
                }

            }
        });

        editHome = (EditText) findViewById(R.id.editHome);
        editHome.setText(settings.getString("Home2", ""));

        editStreet = (EditText) findViewById(R.id.editStreet);
        editStreet.setText(settings.getString("Street2", ""));

        buttonConfirm = (Button) findViewById(R.id.buttonConfirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                home = editHome.getText().toString();
                if (home.length()==0) {
                    Toast.makeText(getApplicationContext(), "Please insert your home number/building name.", Toast.LENGTH_LONG).show();
                }
                else {
                    editor.putString("Home2", editHome.getText().toString());
                    editor.putString("Street2", editStreet.getText().toString());
                    editor.putString("Latitude2", latitude);
                    editor.putString("Longitude2", longitude);
                    editor.commit();
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("address", home);
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                }
            }
        });

        buttonCancel = (Button) findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_address, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_help) {
            Toast.makeText(getApplicationContext(), "Set up your second delivery address, eg: office, friend's house", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed (){
    }

}
