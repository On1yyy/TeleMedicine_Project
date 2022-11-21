package com.example.carryingmedicine.ClinicDetail;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.carryingmedicine.R;
import com.example.carryingmedicine.login.LoginActivity;

public class AlarmForeService extends Service {
    NotificationManager Notifi_M;
    ServiceThread thread;
    Notification Notifi2 ;

    public AlarmForeService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override //화면이 꺼졌을때
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notifi_M = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        myServiceHandler handler = new myServiceHandler();
        thread = new ServiceThread(handler);
        thread.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        thread.stopForever();
        thread = null;//쓰레기 값을 만들어서 빠르게 회수하라고 null을 넣어줌.
    }

    class myServiceHandler extends Handler {
        @Override
        public void handleMessage(android.os.Message msg) {
            NotificationManager notificationManager = (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE );
            Intent intent2 = new Intent(AlarmForeService.this, LoginActivity.class);
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK) ;
            PendingIntent pendingIntent2 = PendingIntent.getActivity(AlarmForeService.this, 0, intent2,PendingIntent.FLAG_UPDATE_CURRENT);


            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                @SuppressLint("WrongConstant")
                NotificationChannel notificationChannel = new NotificationChannel(
                        "my_notification", "n_channel",
                        NotificationManager.IMPORTANCE_MAX);
                notificationChannel.setDescription("description");
                notificationChannel.setName("Channel Name");
                assert notificationManager != null;
                notificationManager.createNotificationChannel(notificationChannel);
            }


            Notifi2 = new NotificationCompat.Builder(AlarmForeService.this, "my_notification")
                    .setSmallIcon(R.drawable.hospital_icon) // 아이콘 설정하지 않으면 오류남
                    .setContentTitle("원격진료 예약 알리미")
                    .setContentText("실행중입니다.")
                    .setContentIntent(pendingIntent2)
                    .setTicker("알림!!!")
                    .build();

            //소리추가
            Notifi2.defaults = Notification.DEFAULT_SOUND;
            //알림 소리를 한번만 내도록
            Notifi2.flags = Notification.FLAG_ONLY_ALERT_ONCE;
            //확인하면 자동으로 알림이 제거 되도록
            Notifi2.flags = Notification.FLAG_AUTO_CANCEL;
            //실행
            startForeground(777,Notifi2);
            Toast.makeText(AlarmForeService.this, "알림 실행", Toast.LENGTH_LONG).show();
        }
    }


}