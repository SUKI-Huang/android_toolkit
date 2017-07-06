package com.holiestar.toolkit.detector;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by Suki on 6/28/2017.
 */

public class ShakeDetector implements SensorEventListener {
    private Context context;
    private SensorManager sensorManager;
    private static final int defaultThreshold = 18;
    private int thresholdX = 18;
    private int thresholdY = 18;
    private int thresholdZ = 18;

    private OnShakeListener onShakeListener;

    public interface OnShakeListener {
        void OnShake();
    }

    public ShakeDetector setOnShakeListener(OnShakeListener onShakeListener) {
        this.onShakeListener = onShakeListener;
        return this;
    }

    public ShakeDetector(Context context) {
        this(context, defaultThreshold);
    }

    public ShakeDetector(Context context, int threshold) {
        this(context, threshold, threshold, threshold);
    }

    public ShakeDetector(Context context, int thresholdX, int thresholdY, int thresholdZ) {
        this.context = context;
        this.sensorManager=(SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.thresholdX = thresholdX;
        this.thresholdY = thresholdY;
        this.thresholdZ = thresholdZ;
    }

    public void register() {
        try {
            sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        } catch (Exception e) {
        }
    }

    public void unregister() {
        try {
            sensorManager.unregisterListener(this);
            sensorManager = null;
        } catch (Exception e) {

        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() != Sensor.TYPE_ACCELEROMETER) {
            return;
        }
        float[] values = sensorEvent.values;
        if (Math.abs(values[0]) > thresholdX || Math.abs(values[1]) > thresholdY || Math.abs(values[2]) > thresholdZ) {
            if (onShakeListener != null) onShakeListener.OnShake();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
