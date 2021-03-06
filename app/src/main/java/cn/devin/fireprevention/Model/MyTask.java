package cn.devin.fireprevention.Model;

import com.tencent.tencentmap.mapsdk.maps.model.LatLng;

/**
 * Created by Devin on 2017/12/27.
 */

public class MyTask {
    //目的地
    LatLng destination;
    //发布时间
    int[] pubTime;
    //主题
    String subject;
    //描述
    String describe;

    public MyTask(LatLng destination,int[] pubTime,String subject,String describe){
        this.destination = destination;
        this.pubTime = pubTime;
        this.subject = subject;
        this.describe = describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public void setDestination(LatLng destination) {
        this.destination = destination;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setPubTime(int[] pubTime) {
        this.pubTime = pubTime;
    }

    public int[] getPubTime() {
        return pubTime;
    }

    public LatLng getDestination() {
        return destination;
    }

    public String getDescribe() {
        return describe;
    }

    public String getSubject() {
        return subject;
    }
}
