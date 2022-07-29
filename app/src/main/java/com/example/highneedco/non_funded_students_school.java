package com.example.highneedco;
import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class non_funded_students_school extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{



    List<responsemodel> data;
    Context context;
    public myadapter adapter;
    RecyclerView recview;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView nav;
    myadapter.RecyclerViewClickListener listener;
    public static String mobile;


    private Toolbar toolbar;
    private NavigationView nav_view;
    private CircleImageView nav_profile_image;
    private TextView nav_fullname, nav_type, nav_email;
    private RecyclerView recyclerview;
    private ProgressBar progressbar;


    private LinearLayoutManager LinearLayoutManager;

    //Age and name Project VAriabless


    LayoutInflater inflater1;

    int count =0;
    int age;
    String name;

    EditText txtname, txtage,txtsearch;

    public static String donorss="";

    String key;

    int temp;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_funded_students_school);

        toolbar =findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("High Need Co");

        drawerLayout =findViewById(R.id.drawerLayout);
        nav_view=findViewById(R.id.nav_view);



        ActionBarDrawerToggle toogle= new ActionBarDrawerToggle(non_funded_students_school.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();

        nav_view.setNavigationItemSelectedListener(this);

        SharedPreferences sp=getSharedPreferences("credentials",MODE_PRIVATE);
        String uemail = sp.getString("email", "defaultValue");
        Log.d(TAG, "Profile Email :" + uemail);
        String utype = sp.getString("type", "defaultValue");

        processname(uemail);


        Log.d(TAG, "My Message Donor from server" + donorss);



       /*  progressbar = findViewById(R.id.progressbar);
       recyclerview= (RecyclerView) findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users"), Model.class)
                        .build();

        myAdapter = new MyAdapter(options);
        recyclerview.setAdapter(myAdapter);

*/

        nav_profile_image = nav_view.getHeaderView(0).findViewById(R.id.nav_user_image);
        nav_fullname = nav_view.getHeaderView(0).findViewById(R.id.nav_username);
        nav_email = nav_view.getHeaderView(0).findViewById(R.id.nav_user_email);
        nav_type = nav_view.getHeaderView(0).findViewById(R.id.nav_user_type);




        nav_email.setText(uemail);
        nav_type.setText(utype);



//Name age code

















        recview= findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));

        processdata();



    }
    /*
        @Override
        protected void onStart() {
            super.onStart();
            myAdapter.startListening();
        }


        @Override
        protected void onStop() {
            super.onStop();
            myAdapter.stopListening();
        }

    */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile:
                Intent intent = new Intent(non_funded_students_school.this, ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.home:
                Intent i = new Intent(non_funded_students_school.this, school_dashboard.class);
                startActivity(i);
                break;
            case R.id.students_non_funded:
                Intent in = new Intent(non_funded_students_school.this, non_funded_students_school.class);
                startActivity(in);
                break;

            case R.id.students_funded:
                Intent inte = new Intent(non_funded_students_school.this, funded_students_school.class);
                startActivity(inte);
                break;
            case R.id.myStudents:
                Intent inten = new Intent(non_funded_students_school.this, mystudents.class);
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






    public void processdata(){
        retrofit2.Call<List<responsemodel>> call=apicontroller.getInstance().getapi().getdatanonfunded();
        call.enqueue(new Callback<List<responsemodel>>() {
            @Override
            public void onResponse(retrofit2.Call<List<responsemodel>> call, Response<List<responsemodel>> response) {
                List<responsemodel> data=response.body();

                listener =new myadapter.RecyclerViewClickListener() {
                    @Override
                    public void onClick(View v, int position) {
                        Intent intent=new Intent(getApplicationContext(), student_details.class);
                        intent.putExtra("name", data.get(position).getName());
                        intent.putExtra("email", data.get(position).getEmail());
                        intent.putExtra("mobile", data.get(position).getMobile());
                        intent.putExtra("income", data.get(position).getIncome());
                        intent.putExtra("fundneeded", data.get(position).getFundneeded());
                        intent.putExtra("school", data.get(position).getSchool());
                        intent.putExtra("donor", data.get(position).getDonor());
                        intent.putExtra("eligibility", data.get(position).getEligibility());
                        intent.putExtra("fatherstatus", data.get(position).getFatherstatus());
                        intent.putExtra("fundstatus", data.get(position).getFundstatus());



                        //  intent.putExtra("imagename",temp.getImgname());

                        startActivity(intent);

                    }
                };




                recview.addItemDecoration(new DividerItemDecoration(non_funded_students_school.this, LinearLayoutManager.VERTICAL));
                adapter= new myadapter(data, listener);
                recview.setAdapter(adapter);

            }

            @Override
            public void onFailure(retrofit2.Call<List<responsemodel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();

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
                donorss=username;
                Log.d(TAG, "\nDonor: " + donorss);
                nav_fullname.setText(donorss);

            }

            @Override
            public void onFailure(Call<login_response_model> call, Throwable t) {
            }
        });

    }


}