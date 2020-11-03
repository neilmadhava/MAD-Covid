package com.example.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logo=findViewById(R.id.imageView5);

        final Animation zoomin= AnimationUtils.loadAnimation(this,R.anim.zoomin);
        final Animation zoomout=AnimationUtils.loadAnimation(this,R.anim.zoomout);

        TimerTask task1=new TimerTask() {
            @Override
            public void run() {
                logo.startAnimation(zoomin);
            }
        };
        TimerTask task2=new TimerTask() {
            @Override
            public void run() {
                logo.startAnimation(zoomout);
            }
        };
        TimerTask task3=new TimerTask() {
            @Override
            public void run() {
                Intent i=new Intent(MainActivity.this,loginpage.class);
                startActivity(i);
            }
        };
        Timer t1=new Timer();
        logo.startAnimation(zoomin);
//        t1.schedule(task2,1000);
//        t1.schedule(task1,2000);
        t1.schedule(task3,1500);
    }
}