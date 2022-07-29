package com.example.highneedco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SelelctRegisterationActivity extends AppCompatActivity {

    private Button buttonSchool, buttonDonar;
    private TextView backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selelct_registeration);
        buttonDonar=findViewById(R.id.buttonDonar);
        buttonSchool=findViewById(R.id.buttonSchool);
        backButton=findViewById(R.id.backButton);

        buttonDonar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SelelctRegisterationActivity.this, DonorRegisterationActivity.class);
                startActivity(intent);
            }
        });

        buttonSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SelelctRegisterationActivity.this, SchoolRegisterationActivity.class);
                startActivity(intent);
            }
        });



        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SelelctRegisterationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}