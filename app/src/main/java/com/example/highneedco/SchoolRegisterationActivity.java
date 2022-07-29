package com.example.highneedco;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.android.material.textfield.TextInputEditText;


import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Callback;
import retrofit2.Response;

public class SchoolRegisterationActivity extends AppCompatActivity {
    private CircleImageView profile_image;
    private TextInputEditText registerFullName, registerCnic, registerNumber, registerIncome, registerEmail, registerPassword;
    private Button registerButton;
    private TextView backButton, tv2;
    private Uri resultUri;
    private ProgressDialog loader;

    boolean isAllFieldsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_registeration);

        profile_image = findViewById(R.id.profile_image);
        registerFullName = findViewById(R.id.registerFullName);
        registerCnic = findViewById(R.id.registerCnic);
        registerNumber = findViewById(R.id.registerNumber);
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        registerButton = findViewById(R.id.registerButton);
        backButton = findViewById(R.id.backButton);
        loader = new ProgressDialog(this);
        tv2=findViewById(R.id.tv2);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SchoolRegisterationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // store the returned value of the dedicated function which checks
                // whether the entered data is valid or if any fields are left blank.
                isAllFieldsChecked = CheckAllFields();

                // the boolean variable turns to be true then
                // only the user must be proceed to the activity2
                if (isAllFieldsChecked) {
                    userregister(registerFullName.getText().toString(), registerCnic.getText().toString(), registerNumber.getText().toString(), registerEmail.getText().toString(), registerPassword.getText().toString());
                }
                }
        });


    }



    public void userregister(String name, String cnic, String mobile, String email, String password)
    {

        retrofit2.Call<signup_response_model> call=apicontroller.getInstance()
                .getapi()
                .getschoolregister(name,cnic,mobile,email,password);

        call.enqueue(new Callback<signup_response_model>() {
            @Override
            public void onResponse(retrofit2.Call<signup_response_model> call, Response<signup_response_model> response) {
                signup_response_model obj=response.body();
                String result=obj.getMessage().trim();
                if(result.equals("inserted"))
                {
                    tv2.setText("Successfully Registered");
                    tv2.setTextColor(Color.GREEN);
                    registerFullName.setText("");
                    registerCnic.setText("");
                    registerEmail.setText("");
                    registerNumber.setText("");
                    registerPassword.setText("");

                }
                if(result.equals("exist"))
                {
                    tv2.setText("Sorry...! You are already registered");
                    tv2.setTextColor(Color.RED);
                    registerFullName.setText("");
                    registerCnic.setText("");
                    registerEmail.setText("");
                    registerNumber.setText("");
                    registerPassword.setText("");
                }
            }

            @Override
            public void onFailure(retrofit2.Call<signup_response_model> call, Throwable t) {
                tv2.setText("Something went wrong");
                tv2.setTextColor(Color.RED);
                registerFullName.setText("");
                registerCnic.setText("");
                registerEmail.setText("");
                registerNumber.setText("");
                registerPassword.setText("");
            }

        });
    }




    private boolean CheckAllFields() {
        if (registerFullName.length() == 0) {
            registerFullName.setError("This field is required");
            return false;
        }

        if (registerCnic.length() == 0) {
            registerCnic.setError("This field is required");
            return false;
        }
        else if (registerCnic.length() < 4) {
            registerCnic.setError("Please Enter a valid Registeration Number");
            return false;
        }

        if (registerEmail.length() == 0) {
            registerEmail.setError("Email is required");
            return false;
        }
        else if (!(registerEmail.getText().toString().contains("@"))) {
            registerEmail.setError("Please Enter a Valid Email");
            return false;
        }
        else if (!(registerEmail.getText().toString().endsWith(".com"))) {
            registerEmail.setError("Please Enter a Valid Email");
            return false;
        }

        if (registerNumber.length() == 0) {
            registerNumber.setError("Phone Number is required");
            return false;
        }
        else if (registerNumber.length() < 11 || registerNumber.length() > 15 ) {
            registerNumber.setError("Please Enter a valid Phone number");
            return false;
        }



        if (registerPassword.length() == 0) {
            registerPassword.setError("This field is required");
            return false;
        }

        else if (registerPassword.length() < 7) {
            registerPassword.setError("Password must contain minimum 6 characters");
            return false;
        }



        // after all validation return true.
        return true;
    }

}