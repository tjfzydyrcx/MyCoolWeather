package com.example.tjf.mycoolweather.MySensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * Created by Administrator on 2018-01-14 0014.
 */
public class OrientationSensor {
    private SensorManager sensorManager;
    private Sensor accelerationsensor;//加速度
    private Sensor magneticsensor;//地磁
    float[] accelermeter = new float[3];
    float[] magnetic = new float[3];
    private OrientationSensor instance;
    private boolean mHasStarted = false;
    MySensorEventListener mySensorEventListener;

    public OrientationSensor() {
    }


    public OrientationSensor getInstance() {

        if (instance == null) {
            instance = new OrientationSensor();
        }
        return instance;
    }

    public void start(Context context) {
        if (mHasStarted) {
            return;
        }
        mHasStarted = true;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerationsensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            magneticsensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            if (accelerationsensor != null && magneticsensor != null) {
                mySensorEventListener = new MySensorEventListener();
                // 注册监听
                sensorManager.registerListener(mySensorEventListener,
                        accelerationsensor, Sensor.TYPE_ACCELEROMETER);
                sensorManager.registerListener(mySensorEventListener, magneticsensor,
                        Sensor.TYPE_MAGNETIC_FIELD);

            }
        }
    }

    public void stop() {
        if (!mHasStarted || sensorManager == null) {
            return;
        }
        mHasStarted = false;
        sensorManager.unregisterListener(mySensorEventListener);
    }


    private class MySensorEventListener implements SensorEventListener {
        //当传感器的数值发生变化时调用该方法
        @Override
        public void onSensorChanged(SensorEvent event) {
            //当value[]数组的下标为0时就是当前的光照强度
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                accelermeter = event.values;
            } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                magnetic = event.values;
            }
            float[] R = new float[9];
            float[] info = new float[3];
            sensorManager.getRotationMatrix(R, null, accelermeter, magnetic);
            sensorManager.getOrientation(R, info);
            Log.e("zhi=", Math.toDegrees(info[0]) + ">>>>>>>" + Math.toDegrees(info[1]) + ">.>>>>>>>" + Math.toDegrees(info[2]));

        }

        //当传感器的精度发生变化时调用该方法
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

}
