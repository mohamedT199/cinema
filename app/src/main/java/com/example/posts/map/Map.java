package com.example.posts.map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.posts.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Map extends AppCompatActivity implements OnMapReadyCallback , View.OnClickListener {

    MapView maap ;
    final  int REQUEST_PERIMISSION = 1000 ;

    @Override
    protected void onStart() {
        super.onStart();
        maap.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        maap.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        maap.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        maap.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        maap.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        maap.onLowMemory();
    }

    GoogleMap googleMap ;
    Location loc = null  ;
    Marker marker ;
    int zoom = 10  ;
    FloatingActionButton add , min ;
    GetLocationFromProvider getLocationFromProvider ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        maap = findViewById(R.id.mapping);
        add = findViewById(R.id.add_map);
        min = findViewById(R.id.min_map);
        getLocationFromProvider = new GetLocationFromProvider(this);
        maap.onCreate(savedInstanceState);
        maap.getMapAsync(this);
        askPermision();
        addMarker();
        add.setOnClickListener(this);
        min.setOnClickListener(this);

    }


    public void askPermision()
    {
        if (PermissionAllaow())
        {

            loc = getLocationFromProvider.getCureentLocation();
        }
        else
        {
            requestPermissionHere();
        }
    }

    private void requestPermissionHere() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this , Manifest.permission.ACCESS_FINE_LOCATION))
        {
            BuildPopMenu("Alert!" , "We Want Location To See Trendes In This Location");
        }
        else
        {
            ActivityCompat.requestPermissions(this ,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION }
            , REQUEST_PERIMISSION);
        }

    }

    private boolean PermissionAllaow() {
        if (ContextCompat.checkSelfPermission(this , Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
        {
            return  true ;
        }
        return  false ;
    }
    public  void BuildPopMenu(String title , String meesage)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this) ;
        alert.setTitle(title);
        alert.setMessage(meesage);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.setCancelable(false);
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode
            , @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUEST_PERIMISSION :
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this, "Thanks For You", Toast.LENGTH_SHORT).show();
                    loc = getLocationFromProvider.getCureentLocation();
                }
                else
                {
                    Toast.makeText(this, "We appricate your help", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap ;
    }

    protected void addMarker()
    {
        if (loc == null || googleMap == null)
        {
            Toast.makeText(this, "There Are Somthing Wrong", Toast.LENGTH_SHORT).show();
        }
        marker = googleMap.addMarker(new MarkerOptions().title("You Are Here").
                position(new LatLng(loc.getLatitude() , loc.getLongitude())));

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loc.getLatitude() , loc.getLongitude()) , zoom));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case  R.id.add_map : {
                if (googleMap != null && loc != null) {
                    if (zoom != 23) {
                        zoom = zoom + 1;
                    }
                    else
                    {
                        zoom = 23 ;
                        Toast.makeText(this, "That is Your Location Big Zoom", Toast.LENGTH_SHORT).show();
                    }
                    googleMap.animateCamera(CameraUpdateFactory.
                            newLatLngZoom(new LatLng(loc.getLatitude(), loc.getLongitude()), zoom));
                }
                break;
            }
            case R.id.min_map :
            {
                if (googleMap != null && loc != null) {
                    if (zoom != 1) {
                        zoom = zoom - 1;
                    }
                    else
                    {
                        zoom = 1 ;
                        Toast.makeText(this, "That is Last View", Toast.LENGTH_SHORT).show();
                    }
                    googleMap.animateCamera(CameraUpdateFactory.
                            newLatLngZoom(new LatLng(loc.getLatitude(), loc.getLongitude()), zoom));
                }
                break;
            }

        }
    }
}
