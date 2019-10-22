package com.example.androidparticlestarter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.particle.android.sdk.cloud.ParticleCloud;
import io.particle.android.sdk.cloud.ParticleCloudSDK;
import io.particle.android.sdk.cloud.ParticleDevice;
import io.particle.android.sdk.cloud.ParticleEvent;
import io.particle.android.sdk.cloud.ParticleEventHandler;
import io.particle.android.sdk.cloud.exceptions.ParticleCloudException;
import io.particle.android.sdk.utils.Async;

public class MainActivity extends AppCompatActivity {
    // MARK: Debug info
    private final String TAG="Rehmat";

    // MARK: Particle Account Info
    private final String PARTICLE_USERNAME = "akshdeepkaur235@gmail.com";
    private final String PARTICLE_PASSWORD = "9463931734";

    // MARK: Particle device-specific info
    private final String DEVICE_ID = "1f0036000e47363333343437";

    // MARK: Particle Publish / Subscribe variables
    private long subscriptionId;

    // MARK: Particle device
    private ParticleDevice mDevice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Initialize your connection to the Particle API
        ParticleCloudSDK.init(this.getApplicationContext());

        // 2. Setup your device variable
        getDeviceFromCloud();


        //CREATING BUTTONS
        Button a = findViewById(R.id.a);
        Button b = findViewById(R.id.b);
        Button c = findViewById(R.id.c);
        Button d = findViewById(R.id.d);
        Button e = findViewById(R.id.e);



        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commandToSend = "\"C2,8\"";
                Log.d(TAG, "Command to send to particle: " + commandToSend);
                deviceData(commandToSend);
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commandToSend = "\"C5,8,D5,8\"";
                Log.d(TAG, "Command to send to particle: " + commandToSend);
                deviceData(commandToSend);
            }
        });

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commandToSend = "\"D3,8\"";
                Log.d(TAG, "Command to send to particle: " + commandToSend);
                deviceData(commandToSend);
            }
        });

        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commandToSend = "\"D5,8,E5,8\"";
                Log.d(TAG, "Command to send to particle: " + commandToSend);
                deviceData(commandToSend);
            }
        });

        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commandToSend = "\"E5,8\"";
                Log.d(TAG, "Command to send to particle: " + commandToSend);
                deviceData(commandToSend);
            }
        });



    }


    /**
     * Custom function to connect to the Particle Cloud and get the device
     */
    public void getDeviceFromCloud() {
        // This function runs in the background
        // It tries to connect to the Particle Cloud and get your device
        Async.executeAsync(ParticleCloudSDK.getCloud(), new Async.ApiWork<ParticleCloud, Object>() {

            @Override
            public Object callApi(@NonNull ParticleCloud particleCloud) throws ParticleCloudException, IOException {
                particleCloud.logIn(PARTICLE_USERNAME, PARTICLE_PASSWORD);
                mDevice = particleCloud.getDevice(DEVICE_ID);
                return -1;
            }

            @Override
            public void onSuccess(Object o) {
                Log.d(TAG, "Successfully got device from Cloud");
            }

            @Override
            public void onFailure(ParticleCloudException exception) {
                Log.d(TAG, exception.getBestMessage());
            }
        });
    }

    //calling this function
    public void deviceData(String commandToSend){
        Async.executeAsync(ParticleCloudSDK.getCloud(), new Async.ApiWork<ParticleCloud, Object>() {
            @Override
            public Object callApi(@NonNull ParticleCloud particleCloud) throws ParticleCloudException, IOException {

                // 2. build a list and put the r,g,b into the list
                List<String> functionParameters = new ArrayList<String>();
                functionParameters.add(commandToSend);

                // 3. send the command to the particle
                try {
                    mDevice.callFunction("music", functionParameters);
                } catch (ParticleDevice.FunctionDoesNotExistException e) {
                    e.printStackTrace();
                }

                return -1;
            }

            @Override
            public void onSuccess(Object o) {
                Log.d(TAG, "Sent music command to device.");
            }

            @Override
            public void onFailure(ParticleCloudException exception) {
                Log.d(TAG, exception.getBestMessage());
            }
        });
    }}


