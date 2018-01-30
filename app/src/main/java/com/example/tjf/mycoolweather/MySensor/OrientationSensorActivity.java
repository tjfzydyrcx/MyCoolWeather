package com.example.tjf.mycoolweather.MySensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.tjf.mycoolweather.BaseActivity;
import com.example.tjf.mycoolweather.R;

/**
 * Created by Administrator on 2018-01-21 0021.
 */
public class OrientationSensorActivity extends BaseActivity {
    private SensorManager mSensorManager;

    private Sensor accelerometer; // 加速度传感器
    private Sensor magnetic; // 地磁场传感器
    MySensorEventListener mySensorEventListener;
    //当value[]数组的下标为0时就是当前的光照强度
    float[] accelermeters = new float[3];
    float[] magnetics = new float[3];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sensor);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        // 初始化加速度传感器
        accelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // 初始化地磁场传感器
        magnetic = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        calculateOrientation();
    }

    @Override
    protected void onResume() {
        if (accelerometer != null && magnetic != null) {
            mySensorEventListener = new MySensorEventListener();
            // 注册监听
            mSensorManager.registerListener(mySensorEventListener,
                    accelerometer, Sensor.TYPE_ACCELEROMETER);
            mSensorManager.registerListener(mySensorEventListener, magnetic,
                    Sensor.TYPE_MAGNETIC_FIELD);

        }

        super.onResume();
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mySensorEventListener);
        super.onPause();
    }


    private class MySensorEventListener implements SensorEventListener {
        //当传感器的数值发生变化时调用该方法
        @Override
        public void onSensorChanged(SensorEvent event) {

            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                accelermeters = event.values;
            } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                magnetics = event.values;
            }
            calculateOrientation();
           /* float[] R = new float[9];
            float[] info = new float[3];
            mSensorManager.getRotationMatrix(R, null, accelermeter, magnetic);
            mSensorManager.getOrientation(R, info);
            Log.e("zhi=", Math.toDegrees(info[0]) + ">>>>>>>" + Math.toDegrees(info[1]) + ">.>>>>>>>" + Math.toDegrees(info[2]));*/

        }

        //当传感器的精度发生变化时调用该方法
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    // 计算方向
    private void calculateOrientation() {
        float[] values = new float[3];
        float[] R = new float[9];
        SensorManager.getRotationMatrix(R, null, accelermeters,
                magnetics);
        SensorManager.getOrientation(R, values);
        values[0] = (float) Math.toDegrees(values[0]);
        Log.e("zhi=",  values[0] + ">>>>>>>" + values[0] + ">.>>>>>>>" +  values[0]);
 /*       if (values[0] >= -5 && values[0] < 5) {
            azimuthAngle.setText("正北");
        } else if (values[0] >= 5 && values[0] < 85) {
            // Log.i(TAG, "东北");
            azimuthAngle.setText("东北");
        } else if (values[0] >= 85 && values[0] <= 95) {
            // Log.i(TAG, "正东");
            azimuthAngle.setText("正东");
        } else if (values[0] >= 95 && values[0] < 175) {
            // Log.i(TAG, "东南");
            azimuthAngle.setText("东南");
        } else if ((values[0] >= 175 && values[0] <= 180)
                || (values[0]) >= -180 && values[0] < -175) {
            // Log.i(TAG, "正南");
            azimuthAngle.setText("正南");
        } else if (values[0] >= -175 && values[0] < -95) {
            // Log.i(TAG, "西南");
            azimuthAngle.setText("西南");
        } else if (values[0] >= -95 && values[0] < -85) {
            // Log.i(TAG, "正西");
            azimuthAngle.setText("正西");
        } else if (values[0] >= -85 && values[0] < -5) {
            // Log.i(TAG, "西北");
            azimuthAngle.setText("西北");
        }*/
    }
}
