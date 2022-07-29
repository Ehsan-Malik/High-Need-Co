package com.example.highneedco;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface apiset
{


    //For Fetching Students
    @GET("json_user_fetch.php")
    Call<List<responsemodel>> getdata();

    //For Fetching Students
    @GET("funded_students.php")
    Call<List<responsemodel>> getdatafunded();

    //For Fetching Students
    @GET("non_funded_students.php")
    Call<List<responsemodel>> getdatanonfunded();


    //for fetching orders
    @GET("myfunding.php")
    Call<List<responsemodel>> getmyfunding(
            @Query("donor") String donor
    );


    //for fetching orders
    @GET("my_students.php")
    Call<List<responsemodel>> getmystudents(
            @Query("school") String school
    );


    @FormUrlEncoded
    @POST("donorsignup.php")
    Call<signup_response_model> getregister(
            @Field("name") String name,
            @Field("cnic") String cnic,
            @Field("mobile") String mobile,
            @Field("income") String income,
            @Field("email") String email,
            @Field("password") String password

    );

    @FormUrlEncoded
    @POST("schoolsignup.php")
    Call<signup_response_model> getschoolregister(
            @Field("name") String name,
            @Field("cnic") String cnic,
            @Field("mobile") String mobile,
            @Field("email") String email,
            @Field("password") String password

    );



    @FormUrlEncoded
    @POST("login.php")
    Call<login_response_model> getlogin(
            @Field("email") String email,
            @Field("password") String password
    );






    @FormUrlEncoded
    @POST("fetch_user_profile.php")
    Call<login_response_model> getname(
            @Field("email") String email
            );



    @FormUrlEncoded
    @POST("feedback.php")
    Call<login_response_model> getfeedback(
            @Field("id") String id,
            @Field("rate") String rate
    );

    @FormUrlEncoded
    @POST("order_status.php")
    Call<login_response_model> getstatus(
            @Field("id") String id,
            @Field("status") String status
    );



    @FormUrlEncoded
    @POST("fund_now.php")
    Call<signup_response_model> fundnow(
            @Field("email") String email,
            @Field("status") String status,
            @Field("donor") String donor
    );


    @FormUrlEncoded
    @POST("addstudent.php")
    Call<signup_response_model> addnewstudent(
            @Field("name") String name,
            @Field("cnic") String cnic,
            @Field("mobile") String mobile,
            @Field("income") String income,
            @Field("fund") String fund,
            @Field("email") String email,
            @Field("school") String school,
            @Field("status") String status);
}

