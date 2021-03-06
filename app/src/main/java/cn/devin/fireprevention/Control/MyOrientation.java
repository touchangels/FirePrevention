package cn.devin.fireprevention.Control;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

import cn.devin.fireprevention.MyApplication;

/**
 * Created by Devin on 2017/12/19.
 * 方向传感器，单例模式
 */

public class MyOrientation implements SensorEventListener{
    private static final String TAG = "MyOrientation";

    private SensorManager mSensorManager;
    private Sensor accelerometer; // 加速度传感器
    private Sensor magnetic; // 地磁场传感器

    private float[] accelerometerValues = new float[3];
    private float[] magneticFieldValues = new float[3];
    private float lastOrient = 0;

    /**
     * 获取单例对象
     * @return
     */
    public static MyOrientation getInstance(){
        return myOrientation;
    }

    /*
    私有的对象
    私有的构造方法，确保该类在外部不能被实例化
    通过 getInstance 方法返回唯一实例
     */
    private static MyOrientation myOrientation = new MyOrientation();
    private MyOrientation(){
        mSensorManager = (SensorManager) MyApplication.getContext().getSystemService(Context.SENSOR_SERVICE);

        // 初始化加速度传感器
        accelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // 初始化地磁场传感器
        magnetic = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        // 注册监听
        mSensorManager.registerListener(this, accelerometer, Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, magnetic, Sensor.TYPE_MAGNETIC_FIELD);
    }

    /**
     * 删除监听器
     */
    public void unRegisterLis(){
        mSensorManager.unregisterListener(this);
    }


    /**
     * 计算方向角
     */
    private void calculateOrientation() {
        float[] values = new float[3];
        float[] R = new float[9];
        SensorManager.getRotationMatrix(R, null, accelerometerValues,
                magneticFieldValues);
        SensorManager.getOrientation(R, values);
        //将弧度切换为角度
        values[0] = (float) Math.toDegrees(values[0]);
        values[1] = (float) Math.toDegrees(values[1]);
        values[2] = (float) Math.toDegrees(values[2]);

        //回调接口
        if(Math.abs(lastOrient - values[0]) > 3) {
            myOrientationListener.onOrientationChange(values);
        }

        //Log.d(TAG, "calculateOrientation: " + values[0]);
//        if (values[0] >= -5 && values[0] < 5) {
//            Toast.makeText(MyApplication.getContext(),"正北",Toast.LENGTH_SHORT).show();
//        } else if (values[0] >= 5 && values[0] < 85) {
//            Toast.makeText(MyApplication.getContext(),"东北",Toast.LENGTH_SHORT).show();
//        } else if (values[0] >= 85 && values[0] <= 95) {
//            Toast.makeText(MyApplication.getContext(),"正东",Toast.LENGTH_SHORT).show();
//        } else if (values[0] >= 95 && values[0] < 175) {
//            Toast.makeText(MyApplication.getContext(),"东南",Toast.LENGTH_SHORT).show();
//        } else if ((values[0] >= 175 && values[0] <= 180)
//                || (values[0]) >= -180 && values[0] < -175) {
//            Toast.makeText(MyApplication.getContext(),"正南",Toast.LENGTH_SHORT).show();
//        } else if (values[0] >= -175 && values[0] < -95) {
//            Toast.makeText(MyApplication.getContext(),"西南",Toast.LENGTH_SHORT).show();
//        } else if (values[0] >= -95 && values[0] < -85) {
//            Toast.makeText(MyApplication.getContext(),"正西",Toast.LENGTH_SHORT).show();
//        } else if (values[0] >= -85 && values[0] < -5) {
//            Toast.makeText(MyApplication.getContext(),"西北",Toast.LENGTH_SHORT).show();
//        }
    }

    /**
     * 传感器更新回调
     * @param sensorEvent 传感器事件
     */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelerometerValues = sensorEvent.values;
        }
        if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            magneticFieldValues = sensorEvent.values;
        }
        calculateOrientation();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    /**
     * 接口，回传方向角
     */
    private MyOrientationListener myOrientationListener;
    public interface MyOrientationListener{
        void onOrientationChange(float[] values);
    }
    public void setOnOrientationChangeListener(MyOrientationListener myOrientationListener){
        this.myOrientationListener = myOrientationListener;
    }

}
