package com.example.toshiba.pcv_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TitleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        Button btnDisplayMainActivity = (Button) findViewById(R.id.button_start);
        btnDisplayMainActivity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TitleActivity.this, LogoActivity.class);
                startActivity(intent);
            }
        });

        Button btnDisplayManualActivity = (Button) findViewById(R.id.button_manual);
        btnDisplayManualActivity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TitleActivity.this, ManualActivity.class);
                startActivity(intent);
            }
        });
    }

}