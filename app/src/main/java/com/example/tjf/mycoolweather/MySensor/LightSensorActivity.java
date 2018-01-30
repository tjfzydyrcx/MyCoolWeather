package com.example.tjf.mycoolweather.MySensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.tjf.mycoolweather.BaseActivity;
import com.example.tjf.mycoolweather.R;
import com.example.tjf.mycoolweather.util.LogUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018-01-14 0014.
 */
public class LightSensorActivity extends BaseActivity {
    private SensorManager manager;
    private Sensor sensor;
    private MySensorEventListener mySensorEventListener;
    @BindView(R.id.button4)
    Button bt;
    @BindView(R.id.button5)
    Button bt1;
    Unbinder s;
    OrientationSensor o;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sensor);
        s = ButterKnife.bind(this);
        o = new OrientationSensor();
        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> aa = manager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : aa) {
            LogUtils.e("数据==" + sensor.getName());
        }
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                o.getInstance().start(LightSensorActivity.this);
            }
        });

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mySensorEventListener = new MySensorEventListener();
                if (manager != null) {
                    //获取光照传感器的实例
                    sensor = manager.getDefaultSensor(Sensor.TYPE_LIGHT);
                    if (sensor != null) {
                        manager.registerListener(mySensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }
                }
            }
        });
    }

    private class MySensorEventListener implements SensorEventListener {
        //当传感器的数值发生变化时调用该方法
        @Override
        public void onSensorChanged(SensorEvent event) {
            //当value[]数组的下标为0时就是当前的光照强度
            float value = event.values[0];
            LogUtils.e(value + ">>>>>>>>>>>>");
        }

        //当传感器的精度发生变化时调用该方法
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    @Override
    protected void onPause() {
        o.getInstance().stop();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (manager != null) {
            manager.unregisterListener(mySensorEventListener);
        }
        if (s != null) {
            s.unbind();
        }
        o.getInstance().stop();

    }
}
