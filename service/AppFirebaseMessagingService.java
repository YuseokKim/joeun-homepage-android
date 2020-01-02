package com.joen.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.joen.MainActivity;
import com.joen.R;

import java.util.Map;

public class AppFirebaseMessagingService extends FirebaseMessagingService {
    private String TAG = "onMessagedReceived";

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d(TAG, "Refreshed onNewToken: " + s);
        sendRegistrationToServer(s);
    }

    public void sendRegistrationToServer(String token){

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Map<String, String> dataMap  = null;
        // Check if message contains a data payload
        if(remoteMessage.getData().size() > 0 ){
            Log.d(TAG, "Message data payload:" + remoteMessage.getData());

            // if data needs to be processed by long running job(10 seconds of more), use Firebase Job Dispatcher

            // else than, handle message within 10 seconds
            dataMap = remoteMessage.getData();
        }

        // Check if message contains a notification payload
        if(remoteMessage.getNotification() != null){
            Log.d(TAG, "message Notification Body: " + remoteMessage.getNotification().getBody());
        }


        //알림 메소드
        sendNotification( remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), dataMap);
    }

    public void sendNotification(String title, String body , Map<String,String> data){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle = new Bundle();
        bundle.putString("phone", data.get("phone"));
        bundle.putString("id", data.get("id"));
        intent.putExtras(bundle);


        //PendingIntent: intent를 생성하여 다른 컴포넌트(자신과 동일한 권한 부여)에게 특정 작업을 요청
        //Intent: 컴포넌트에서 다른 컴포넌트에게 작업을 요청
        //일회용 PendingIntent
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT );

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //알림 소리 설정
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        NotificationCompat.Builder notificationBuilder = null;
        if(Build.VERSION.SDK_INT>=26){
            //오레오 버전 이상일 시
            NotificationChannel mChannel = new NotificationChannel("channel_id", "joeun", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(mChannel);
            notificationBuilder = new NotificationCompat.Builder(this, mChannel.getId());
        }else{
            notificationBuilder = new NotificationCompat.Builder(this);
        }


        //NotificationCompat.Builder -> 알림 설정의 핵심적인 역할

        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(contentIntent);

        notificationManager.notify(0, notificationBuilder.build());
    }
}
