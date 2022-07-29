package com.example.highneedco;

public class responsemodel
{
    String id, name, email, mobile, school, income, fundneeded, fundstatus, eligibility, donor, fatherstatus, image;

    public responsemodel() {
    }

    public responsemodel(String id, String name, String email, String mobile, String school, String income, String fundneeded, String fundstatus, String eligibility, String donor, String fatherstatus, String image) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.school = school;
        this.income = income;
        this.fundneeded = fundneeded;
        this.fundstatus = fundstatus;
        this.eligibility = eligibility;
        this.donor = donor;
        this.fatherstatus = fatherstatus;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getSchool() {
        return school;
    }

    public String getIncome() {
        return income;
    }

    public String getFundneeded() {
        return fundneeded;
    }

    public String getFundstatus() {
        return fundstatus;
    }

    public String getEligibility() {
        return eligibility;
    }

    public String getDonor() {
        return donor;
    }

    public String getFatherstatus() {
        return fatherstatus;
    }

    public String getImage() {
        return image;
    }
}
