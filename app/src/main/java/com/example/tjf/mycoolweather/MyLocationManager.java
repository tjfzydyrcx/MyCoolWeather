package com.example.tjf.mycoolweather;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.tjf.mycoolweather.util.LogUtils;

import java.io.IOException;
import java.security.PublicKey;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 2018-05-31 0031.
 */

public class MyLocationManager {
    LocationManager locationManager;
    Context context;

    public MyLocationManager(final Context context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public void getaddress() {
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 500, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double latitude = location.getLatitude();
                    //得到经度
                    double longitude = location.getLongitude();

                    LogUtils.e("loca" + latitude + longitude);
                    List<Address> locationList = null;
                    Geocoder gc = new Geocoder(context, Locale.getDefault());
                    try {
                        locationList = gc.getFromLocation(latitude, longitude, 1);

                        Address address = locationList.get(0);//得到Address实例
//Log.i(TAG, "address =" + address);
                        String countryName = address.getCountryName();//得到国家名称，比方：中国
                        LogUtils.e("loca" + "countryName = " + countryName);
                        String locality = address.getLocality();//得到城市名称，比方：北京市
                        LogUtils.i("loca" + "locality = " + locality);
                        for (int i = 0; address.getAddressLine(i) != null; i++) {
                            String addressLine = address.getAddressLine(i);//得到周边信息。包含街道等。i=0，得到街道名称
                            LogUtils.i("loca" + "addressLine = " + addressLine);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        } else {
            //无法定位：1、提示用户打开定位服务；2、跳转到设置界面
            Toast.makeText(context, "无法定位，请打开定位服务", Toast.LENGTH_SHORT).show();
            Intent i = new Intent();
            i.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            context.startActivity(i);
        }
    }
}
