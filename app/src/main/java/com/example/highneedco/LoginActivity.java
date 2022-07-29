package com.example.highneedco;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {
    private Button loginButton;
    private TextInputEditText loginEmail, loginPassword;
    private ProgressDialog loader;
    private TextView status;
    public static String type;
    boolean isAllFieldsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        TextView selectButton = findViewById(R.id.selectButton);
    loginButton=findViewById(R.id.loginButton);
    loginEmail=findViewById(R.id.loginEmail);
    loginPassword=findViewById(R.id.loginPassword);
    status=findViewById(R.id.status);
    loader = new ProgressDialog(this);

    selectButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(LoginActivity.this, SelelctRegisterationActivity.class);
            startActivity(intent);
        }
    });


        verifyuserexistence();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // store the returned value of the dedicated function which checks
                // whether the entered data is valid or if any fields are left blank.
                isAllFieldsChecked = CheckAllFields();

                // the boolean variable turns to be true then
                // only the user must be proceed to the activity2
                if (isAllFieldsChecked) {

                    processlogin(loginEmail.getText().toString(), loginPassword.getText().toString());
                }

                }
        });


    }



    public void processlogin(String loginemail, String loginpassword){
        retrofit2.Call<login_response_model> call=apicontroller.getInstance()
                .getapi()
                .getlogin(loginemail,loginpassword);

        call.enqueue(new Callback<login_response_model>() {
            @Override
            public void onResponse(retrofit2.Call<login_response_model> call, retrofit2.Response<login_response_model> response) {
                login_response_model obj=response.body();
                String result=obj.getMessage().trim();
                if(result.equals("school"))
                {
                    Log.d(TAG, "Type :" + result);
                    SharedPreferences sp =getSharedPreferences("credentials", MODE_PRIVATE);
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putString("email", loginemail);
                    editor.putString("password", loginpassword);
                    editor.putString("type", "school");
                    editor.commit();
                    editor.apply();

                    startActivity(new Intent(getApplicationContext(), school_dashboard.class));
                    finish();



                }


                else if(result.equals("donor"))
                {
                    Log.d(TAG, "Type :" + result);
                    SharedPreferences sp =getSharedPreferences("credentials", MODE_PRIVATE);
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putString("email", loginemail);
                    editor.putString("password", loginpassword);
                    editor.putString("type", "donor");

                    editor.commit();
                    editor.apply();

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
                else if(result.equals("notexist"))
                {
                    status.setText("Invalid Email/Password");
                    status.setTextColor(Color.RED);


                }
            }

            @Override
            public void onFailure(retrofit2.Call<login_response_model> call, Throwable t) {
                status.setText("Something went wrong");
                status.setTextColor(Color.RED);
            }
        });

    }

    public void verifyuserexistence(){
        SharedPreferences sp=getSharedPreferences("credentials", MODE_PRIVATE);
        if(sp.contains("email")){
            type = sp.getString("type", "defaultValue");
            Log.d(TAG, "Type :" + type);
            if(type.equals("donor")){
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }
            else{
                startActivity(new Intent(getApplicationContext(), school_dashboard.class));

            }

        }
        else{

        }
    }


    private boolean CheckAllFields() {



        if (loginEmail.length() == 0) {
            loginEmail.setError("Email is required");
            return false;
        }
        else if (!(loginEmail.getText().toString().contains("@"))) {
            loginEmail.setError("Please Enter a Valid Email");
            return false;
        }
        else if (!(loginEmail.getText().toString().endsWith(".com"))) {
            loginEmail.setError("Please Enter a Valid Email");
            return false;
        }



        if (loginPassword.length() == 0) {
            loginPassword.setError("This field is required");
            return false;
        }

        else if (loginPassword.length() < 7) {
            loginPassword.setError("Password must contain minimum 6 characters");
            return false;
        }




        // after all validation return true.
        return true;
    }


}