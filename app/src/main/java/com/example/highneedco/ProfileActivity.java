package com.example.highneedco;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    public static String myname="";

    private Toolbar toolbar;
    private TextView type, name, email, cnic, phonenumber;
    private CircleImageView profileimage;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        type = findViewById(R.id.type);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        profileimage = findViewById(R.id.profileimage);


        SharedPreferences sp=getSharedPreferences("credentials",MODE_PRIVATE);
        String uemail = sp.getString("email", "defaultValue");
        Log.d(TAG, "Profile Email :" + uemail);
        String utype = sp.getString("type", "defaultValue");
        Log.d(TAG, "Profile Type :" + utype);

        processname(uemail);

        email.setText(uemail);
        type.setText(utype);



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }


    public void processname(String email){
        Log.d(TAG, "My Email :" + email);

        Call<login_response_model> call=apicontroller.getInstance()
                .getapi()
                .getname(email);

        call.enqueue(new Callback<login_response_model>() {
            @Override
            public void onResponse(Call<login_response_model> call, Response<login_response_model> response) {
                login_response_model obj=response.body();
                Log.d(TAG, "My Message Response from server" + obj.getMessage());
                String username=obj.getMessage();
                Log.d(TAG, "My Name from server" + username);

                name.setText(username);
                myname=username;
                Log.d(TAG, "My Name for All" + myname);

            }

            @Override
            public void onFailure(Call<login_response_model> call, Throwable t) {
                name.setText("Something went wrong");
            }
        });

    }


}