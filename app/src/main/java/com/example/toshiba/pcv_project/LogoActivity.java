package com.example.toshiba.pcv_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class LogoActivity extends TitleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        ImageView ImageButtonCamera = (ImageView) findViewById(R.id.imageView_camera);
        ImageButtonCamera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogoActivity.this, TakePicture.class);
                startActivity(intent);
            }
        });

        ImageView ImageButtonFile = (ImageView) findViewById(R.id.imageView_file);
        ImageButtonFile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
