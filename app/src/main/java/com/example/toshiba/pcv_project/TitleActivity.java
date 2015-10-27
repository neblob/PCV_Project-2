package com.example.toshiba.pcv_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class TitleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        ImageView ImageButtonPower = (ImageView) findViewById(R.id.imageView_power);
        ImageButtonPower.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TitleActivity.this, LogoActivity.class);
                startActivity(intent);
            }
        });

        ImageView ImageButtonManual = (ImageView) findViewById(R.id.imageView_manual);
        ImageButtonManual.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TitleActivity.this, ManualActivity.class);
                startActivity(intent);
            }
        });
    }

}