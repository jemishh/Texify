package com.example.texify.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.texify.R;

public class SplashScreen extends AppCompatActivity {
    TextView tv_fsense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        init();
    }

    private void init() {

        //initialize
        tv_fsense = findViewById(R.id.tv_texify);
        Thread thread=new Thread() {
            public void run(){
                try{
                    sleep(3500);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                }
            }
        };thread.start();
    }
}