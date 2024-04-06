package com.example.finalappproject.Classes;

public class Student {
    private String name;
    private String email;
    private String instituteIsrael;
    private String instituteAbroad;
    private String degree;
    private String countryAbroad;
    private String city;

    public Student(String name, String email, String instituteIsrael, String instituteAbroad,
                   String degree, String countryAbroad, String city) {
        this.name = name;
        this.email = email;
        this.instituteIsrael = instituteIsrael;
        this.instituteAbroad = instituteAbroad;
        this.degree = degree;
        this.countryAbroad = countryAbroad;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInstituteIsrael() {
        return instituteIsrael;
    }

    public void setInstituteIsrael(String instituteIsrael) {
        this.instituteIsrael = instituteIsrael;
    }

    public String getInstituteAbroad() {
        return instituteAbroad;
    }

    public String getCity() {
        return city;
    }

    public void setInstituteAbroad(String instituteAbroad) {
        this.instituteAbroad = instituteAbroad;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getCountryAbroad() {
        return countryAbroad;
    }

    public void setCountryAbroad(String countryAbroad) {
        this.countryAbroad = countryAbroad;
    }
}
