package com.bwie.day0618_oldweekexam_ab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    int num = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                if (num > 0) {

                    num --;

                }else{

                    timer.cancel();

                    Intent intent = new Intent(MainActivity.this,TwoActivity.class);
                    startActivity(intent);

                }

            }
        };

        timer.schedule(task,0,1000);
    }
}
