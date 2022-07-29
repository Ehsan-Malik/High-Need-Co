package com.example.highneedco;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class donorstudent_details extends AppCompatActivity{



    List<responsemodel> data;
    private myadapter.myviewholder holder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donorstudent_details);
        setTitle("Home Bakery App");



        TextView name = findViewById(R.id.name);
        TextView email = findViewById(R.id.email);
        TextView mobile = findViewById(R.id.mobile);
        TextView fundneeded = findViewById(R.id.fundneeded);
        TextView income = findViewById(R.id.income);
        TextView eligibility = findViewById(R.id.eligibility);
        TextView school = findViewById(R.id.school);
        TextView donor = findViewById(R.id.donor);
        TextView fundstatus = findViewById(R.id.fundstatus);
        TextView fatherstatus = findViewById(R.id.fatherstatus);
        Button pay=findViewById(R.id.payBtn);




        String Name = "";
        String Email = "";
        String Mobile = "";
        String Income = "";
        String Fundneeded = "";
        String Eligibility = "";
        String School = "";
        String Donor = "";
        String Fundstatus = "";
        String Fatherstatus = "";




        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            Name = extras.getString("name");
            Email = extras.getString("email");
            Mobile = extras.getString("mobile");
            Income = extras.getString("income");
            Fundneeded = extras.getString("fundneeded");
            Donor = extras.getString("donor");
            School = extras.getString("school");
            Fundstatus = extras.getString("fundstatus");
            Fatherstatus = extras.getString("fatherstatus");
            Eligibility = extras.getString("eligibility");

        }


        //prodimage.setImageDrawable(Drawable.createFromPath(photo));

        name.setText(Name);
        email.setText(Email);
        mobile.setText(Mobile);
        income.setText(Income);
        fundneeded.setText(Fundneeded);
        donor.setText(Donor);
        school.setText(School);
        fundstatus.setText(Fundstatus);
        fatherstatus.setText(Fatherstatus);
        eligibility.setText(Eligibility);


        String finalName = Name;
        String finalEmail = Email;
        String finalMobile = Mobile;
        String finalFundneeded = Fundneeded;


        Log.d(TAG, "My Name" + finalName);
        Log.d(TAG, "My Email" + finalEmail);
        Log.d(TAG, "My Mobile" + finalMobile);
        Log.d(TAG, "My Fund" + finalFundneeded);


        String finalFundstatus = Fundstatus;
        pay.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        if(fundstatus.getText().equals("Funded")){
            Log.d(TAG, "Already Funded" );
            Toast.makeText(getApplicationContext(),"Sorry! This student is already Funded",Toast.LENGTH_SHORT).show();

        }

        else {

            Log.d(TAG, "Not Already Funded" );

            Intent intent = new Intent(getApplicationContext(), easyPaisa.class);
            intent.putExtra("name", finalName);
            intent.putExtra("email", finalEmail);
            intent.putExtra("mobile", finalMobile);
            intent.putExtra("fundneeded", finalFundneeded);
            //  intent.putExtra("imagename",temp.getImgname());

            startActivity(intent);

        }
        }
});

    }



}






