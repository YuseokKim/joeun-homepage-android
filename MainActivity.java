package com.joen;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.joen.domain.Device;
import com.joen.service.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent receiveIntent = getIntent();

        Bundle bundle = receiveIntent.getExtras();
        if(bundle != null){
            String phone = bundle.getString("phone");
            Log.d(TAG, "phone -> " + phone);

        }else{
            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                @Override
                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                    if(!task.isSuccessful()){
                        Log.w(TAG, "getInstanceId failed", task.getException());
                        return;
                    }
                    String token = task.getResult().getToken();
                    sendRequest(token);

                    Log.d(TAG, "currently token is " + token);
                    Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    public void sendRequest(String token){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.joeun_server_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        retrofitService.postToken(new Device(Build.MODEL, token)).enqueue(new Callback<Device>() {
            @Override
            public void onResponse(Call<Device> call, Response<Device> response) {
                if(response.isSuccessful()){
                    Device result = response.body();
                    Log.d(TAG, "getPost is Success");
                    Log.d(TAG,"Fcm Token is " + result.getFcmToken());
                    Log.d(TAG,"Fcm mdeol is " + result.getModel());
                }else{
                    Log.d(TAG, "getPost is fail");
                }
            }

            @Override
            public void onFailure(Call<Device> call, Throwable t) {
                t.printStackTrace();

                Log.d(TAG,t.getMessage());
                Log.d(TAG, "getPost is fail");

            }
        });


    }
}
