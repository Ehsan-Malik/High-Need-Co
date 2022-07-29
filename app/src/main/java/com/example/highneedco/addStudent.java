package com.example.highneedco;

import static android.content.ContentValues.TAG;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class addStudent extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    public NavigationView nav_view;
    private CircleImageView nav_profile_image;
    private TextView nav_fullname, nav_type, nav_email;
    private RecyclerView recyclerview;
    private ProgressBar progressbar;
    public static String donora="";

    private androidx.recyclerview.widget.LinearLayoutManager LinearLayoutManager;

    //Age and name Project VAriabless

    public static final int PICK_IMAGE = 100;

    Service service;

    LayoutInflater inflater1;

    int count =0;
    int age;
    String name;

    EditText txtname, txtage,txtsearch;


    String key;

    int temp;









    CircleImageView imageView;
    EditText registerFullName, registerCnic, registerNumber, registerIncome, registerEmail, registerSchool, registerFund;
    private static final int STORAGE_PERMISSION_CODE = 4655;
    private int PICK_IMAGE_REQUEST = 1;
    private  static final int IMAGE = 100;
    private Uri filepath;
    private Bitmap bitmap;
    TextView tv;
    private RadioGroup radiofatherGroup;
    private RadioButton radiofatherButton;
    Button registerButtton;
    String status;
    public static final String UPLOAD_URL = "https://trafficwardenatd.com/highneedcoapi/addstudent.php";




    boolean isAllFieldsChecked = false;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("High Need Co");

        drawerLayout = findViewById(R.id.drawerLayout);
        nav_view = findViewById(R.id.nav_view);
        radiofatherGroup = findViewById(R.id.fatherStatus);


        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(addStudent.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();

        nav_view.setNavigationItemSelectedListener(this);

        SharedPreferences sp=getSharedPreferences("credentials",MODE_PRIVATE);
        String uemail = sp.getString("email", "defaultValue");
        Log.d(TAG, "Profile Email :" + uemail);
        String utype = sp.getString("type", "defaultValue");

        processname(uemail);


        Log.d(TAG, "My Message Donor from server" + donora);






        nav_profile_image = nav_view.getHeaderView(0).findViewById(R.id.nav_user_image);
        nav_fullname = nav_view.getHeaderView(0).findViewById(R.id.nav_username);
        nav_email = nav_view.getHeaderView(0).findViewById(R.id.nav_user_email);
        nav_type = nav_view.getHeaderView(0).findViewById(R.id.nav_user_type);



        nav_email.setText(uemail);
        nav_type.setText(utype);

//Name age code


        imageView = (CircleImageView) findViewById(R.id.imageView);
        registerFullName = (EditText) findViewById(R.id.registerFullName);
        registerCnic = (EditText) findViewById(R.id.registerCnic);
        registerNumber = (EditText) findViewById(R.id.registerNumber);

        registerIncome = (EditText) findViewById(R.id.registerIncome);

        registerEmail = (EditText) findViewById(R.id.registerEmail);

        registerFund = (EditText) findViewById(R.id.registerFund);

        registerSchool = (EditText) findViewById(R.id.registerSchool);
        registerButtton=(Button) findViewById(R.id.registerButton);



        tv = (TextView) findViewById(R.id.tv);





        registerButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radiofatherGroup.getCheckedRadioButtonId();


                if((selectedId!=-1)) {
                    radiofatherButton = findViewById(selectedId);
                    if (radiofatherButton.getText().equals("Alive")) {
                        status="Alive";
                        Log.d(TAG, "My Radio Button" + selectedId + status);

                    } else if (radiofatherButton.getText().equals("Deceased")) {
                        status="Deceased";
                        Log.d(TAG, "My Radio Button" + selectedId +status);
                    } else {
                        Log.d(TAG, "My Radio Button" + selectedId);
                        Toast.makeText(addStudent.this, "Select Father Status first!", Toast.LENGTH_SHORT).show();

                    }



                }

                else{
                    Log.d(TAG, "My Radio Button" + selectedId);
                    Toast.makeText(addStudent.this, "Fill up the required information!",Toast.LENGTH_SHORT).show();

                }

                // store the returned value of the dedicated function which checks
                // whether the entered data is valid or if any fields are left blank.
                isAllFieldsChecked = CheckAllFields();

                // the boolean variable turns to be true then
                // only the user must be proceed to the activity2
                if (isAllFieldsChecked) {


                    addnewstudent(registerFullName.getText().toString(), registerCnic.getText().toString(), registerNumber.getText().toString(), registerIncome.getText().toString(), registerFund.getText().toString(), registerEmail.getText().toString(), registerSchool.getText().toString(), status);

                }

            }
        });

    }
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile:
                Intent intent = new Intent(addStudent.this, ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.home:
                Intent i = new Intent(addStudent.this, school_dashboard.class);
                startActivity(i);
                break;
            case R.id.students_non_funded:
                Intent in = new Intent(addStudent.this, non_funded_students_school.class);
                startActivity(in);
                break;

            case R.id.students_funded:
                Intent inte = new Intent(addStudent.this, funded_students_school.class);
                startActivity(inte);
                break;
            case R.id.myStudents:
                Intent inten = new Intent(addStudent.this, mystudents.class);
                startActivity(inten);
                break;


            case R.id.logout:

                drawerLayout.closeDrawer(GravityCompat.START);
                SharedPreferences sp=getSharedPreferences("credentials",MODE_PRIVATE);
                sp.edit().remove("email").commit();
                sp.edit().remove("password").commit();
                sp.edit().remove("type").commit();

                sp.edit().apply();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }






    public void addnewstudent(String name, String cnic, String mobile, String income, String fund, String email, String school, String status)
    {

        retrofit2.Call<signup_response_model> call=apicontroller.getInstance()
                .getapi()
                .addnewstudent(name,cnic,mobile,income,fund,email,school,status);

        call.enqueue(new Callback<signup_response_model>() {
            @Override
            public void onResponse(retrofit2.Call<signup_response_model> call, Response<signup_response_model> response) {
                signup_response_model obj=response.body();
                String result=obj.getMessage().trim();
                if(result.equals("Inserted"))
                {
                    tv.setText("Successfully Registered");
                    tv.setTextColor(Color.GREEN);
                    registerFullName.setText("");
                    registerCnic.setText("");
                    registerEmail.setText("");
                    registerNumber.setText("");
                    registerIncome.setText("");
                    registerFund.setText("");
                    registerSchool.setText("");



                }
                if(result.equals("exist"))
                {
                    tv.setText("Sorry...! Student already registered");
                    tv.setTextColor(Color.RED);
                    registerFullName.setText("");
                    registerCnic.setText("");
                    registerEmail.setText("");
                    registerNumber.setText("");
                    registerIncome.setText("");
                    registerFund.setText("");
                    registerSchool.setText("");
                }
            }

            @Override
            public void onFailure(retrofit2.Call<signup_response_model> call, Throwable t) {
                tv.setText("Something went wrong");
                tv.setTextColor(Color.RED);
                registerFullName.setText("");
                registerCnic.setText("");
                registerEmail.setText("");
                registerNumber.setText("");
                registerIncome.setText("");
                registerFund.setText("");
                registerSchool.setText("");
            }

        });
    }

    public void processname(String email){
        Log.d(TAG, "My Email :" + email);

        retrofit2.Call<login_response_model> call=apicontroller.getInstance()
                .getapi()
                .getname(email);

        call.enqueue(new Callback<login_response_model>() {
            @Override
            public void onResponse(retrofit2.Call<login_response_model> call, Response<login_response_model> response) {
                login_response_model obj=response.body();
                Log.d(TAG, "My Message Response from server" + obj.getMessage());
                String username=obj.getMessage();
                Log.d(TAG, "My Name from server" + username);
                donora=username;
                Log.d(TAG, "\nDonor: " + donora);
                nav_fullname.setText(donora);

            }

            @Override
            public void onFailure(Call<login_response_model> call, Throwable t) {
            }
        });

    }





    // function which checks all the text fields
    // are filled or not by the user.
    // when user clicks on the PROCEED button
    // this function is triggered.
    private boolean CheckAllFields() {
        if (registerFullName.length() == 0) {
            registerFullName.setError("This field is required");
            return false;
        }

        if (registerCnic.length() == 0) {
            registerCnic.setError("This field is required");
            return false;
        }
        else if (registerCnic.length() < 13 || registerCnic.length() > 13 ) {
            registerCnic.setError("CNIC must be of 13 digits");
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

        if (registerFund.length() == 0) {
            registerFund.setError("This field is required");
            return false;
        }


        if (registerIncome.length() == 0) {
            registerIncome.setError("This field is required");
            return false;
        }

        if (registerSchool.length() == 0) {
            registerSchool.setError("This field is required");
            return false;
        }

        // after all validation return true.
        return true;
    }
}