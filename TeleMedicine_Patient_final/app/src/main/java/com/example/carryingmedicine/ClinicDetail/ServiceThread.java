package com.example.carryingmedicine.ClinicDetail;

import android.os.Handler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ServiceThread extends Thread {
    Handler handler ; //import 안드로이드 껄로 하기
    boolean isRun = true;
    ClinicDtail clinicDtail;
    Date date1,date2;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
    String a= clinicDtail.reservationDateString;

    public ServiceThread(Handler handler){
        this.handler = handler;
    }
    long ti=0;
    public void set_time(long ti)
    {
        this.ti = ti;
    }
    public void stopForever(){
        synchronized (this) {
            this.isRun = false;
        }
    }

    public void run(){
        //반복적으로 수행할 작업을 한다.
        //handler.sendEmptyMessage(0);//쓰레드에 있는 핸들러에게 메세지를 보냄
        while(isRun){
            try{
               //System.out.println(date1.getTime());
               handler.sendEmptyMessage(0);//쓰레드에 있는 핸들러에게 메세지를 보냄
                break;
           }catch (Exception e) {
            }
        }
    }
}
