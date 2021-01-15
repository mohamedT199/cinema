package com.example.posts.map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import java.util.List;

@SuppressLint("MissingPermission")
public class GetLocationFromProvider {
    LocationManager locationManager ;
    Location location , last ;

    public GetLocationFromProvider(Context con ) {
        locationManager = (LocationManager) con.getSystemService(Context.LOCATION_SERVICE);
        location = null ;
        last = null ;
    }

    protected boolean getOnlineProvider()
    {
        boolean gbs = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return gbs || network ;
    }

    protected Location getCureentLocation()
    {
        if (!getOnlineProvider())
        {
            return null ;
        }
        String provider = LocationManager.GPS_PROVIDER ;
        if (!locationManager.isProviderEnabled(provider))
        {
            provider = LocationManager.NETWORK_PROVIDER ;
        }
        location = locationManager.getLastKnownLocation(provider);
        if (location != null)
        {
            last = location ;
        }
        if (location == null)
        {
            location = getBestLocation();
            if (location == null )
            {
                location = last ;
            }
        }


        return location ;

    }

    private Location getBestLocation() {
        Location loc = null  , temp = null  ;
        List<String> list  = locationManager.getAllProviders();
        for (String provide : list ) {
            temp = locationManager.getLastKnownLocation(provide);
            if (loc == null )
            {
                loc = temp ;
            }
            else
            {
                if (loc.getAccuracy() < temp.getAccuracy())
                {
                    loc = temp ;
                }
            }
        }
        return  loc ;
    }
}
