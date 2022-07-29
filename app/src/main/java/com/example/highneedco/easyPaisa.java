package com.example.highneedco;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class easyPaisa extends AppCompatActivity {

    Button payBtn;
    EditText accountno;
    public static String Donor="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_paisa);
        accountno=findViewById(R.id.accountNumber);
        payBtn=findViewById(R.id.paybtn);




        String Name = "";
        String Email = "";
        String Mobile = "";
        String Fundneeded = "";
        String Fundstatus = "Funded";

        SharedPreferences sp=getSharedPreferences("credentials",MODE_PRIVATE);
        String uemail = sp.getString("email", "defaultValue");
        Log.d(TAG, "Profile Email :" + uemail);
        processname(uemail);



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Name = extras.getString("name");
            Email = extras.getString("email");
            Mobile = extras.getString("mobile");
            Fundneeded = extras.getString("fundneeded");
        }



        Log.d(TAG, "\nReceiver Account: " + Mobile);
        Log.d(TAG, "\nReceiver Name: " + Name);
        Log.d(TAG, "\nReceiver Email: " + Email);
        Log.d(TAG, "\nFund Amount: " + Fundneeded);
        Log.d(TAG, "\nFund Status: " + Fundstatus);


        String finalEmail = Email;
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils. isEmpty(accountno. getText()))
                {
                    accountno.setError("Please Enter the Easypaisa Account Number");
                }else {
                    String donoraccountnumber=accountno.getText().toString();
                    String tempaccount = donoraccountnumber.substring(1);
                    String senderaccount = "92"+tempaccount;

                    Log.d(TAG, "\nDonor Easypaisa account number: " + donoraccountnumber);
                    Log.d(TAG, "\nTemporary account number: " + tempaccount);
                    Log.d(TAG, "\nSender account number: " + senderaccount);

                    SharedPreferences sp=getSharedPreferences("credentials",MODE_PRIVATE);
                    String mobile = sp.getString("mobile", "defaultValue");
                    Log.d(TAG, "\nDonor: " + Donor);


                    fundnow(finalEmail,Fundstatus,Donor);
                    //payment(checkout.orderID,senderaccount);
                    startActivity(new Intent(getApplicationContext(),orderSuccess.class));
                }

            }
        });





    }


/*
    public void payment(String id, String accountno)
    {
        Call<orderresponsemodel> call=apicontroller.getInstance()
                .getapi()
                .payment(id,accountno);

        call.enqueue(new Callback<orderresponsemodel>() {
            @Override
            public void onResponse(Call<orderresponsemodel> call, Response<orderresponsemodel> response) {
                orderresponsemodel obj=response.body();
                String result=obj.getMessage().trim();
                checkout.orderID=result;
            }

            @Override
            public void onFailure(Call<orderresponsemodel> call, Throwable t) {
                Toast.makeText(easyPaisa.this, "Something went wrong",Toast.LENGTH_SHORT).show();


            }
        });
    }


    */




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
                Donor=username;
                Log.d(TAG, "\nDonor: " + Donor);

            }

            @Override
            public void onFailure(Call<login_response_model> call, Throwable t) {
            }
        });

    }



    public void fundnow(String email, String status, String donor)
    {

        Call<signup_response_model> call=apicontroller.getInstance()
                .getapi()
                .fundnow(email,status,donor);

        call.enqueue(new Callback<signup_response_model>() {
            @Override
            public void onResponse(Call<signup_response_model> call, Response<signup_response_model> response) {
                signup_response_model obj=response.body();
                String result=obj.getMessage().trim();
                if(result.equals("inserted")) {
                    Log.d(TAG, "Successfully Inserted");
                }
                else{
                    Log.d(TAG, "Not Inserted");
                }

            }

            @Override
            public void onFailure(Call<signup_response_model> call, Throwable t) {
                Toast.makeText(easyPaisa.this, "Something went wrong",Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Connection Problems");


            }
        });
    }

}