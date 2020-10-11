package com.example.mobileticketbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class splashscreen extends AppCompatActivity {
Animation topanim,bottomanim;
ImageView image;
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);

        topanim= AnimationUtils.loadAnimation(this,R.anim.top_anim);
        bottomanim=AnimationUtils.loadAnimation(this,R.anim.botton_anim);
        image=(ImageView)findViewById(R.id.ss);
        textView=(TextView)findViewById(R.id.spText);

        image.setAnimation(topanim);
        textView.setAnimation(bottomanim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(splashscreen.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },5000);
    }
}