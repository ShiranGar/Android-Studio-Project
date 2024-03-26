package com.example.finalappproject;

public class Student {
    private String name;
    private String email;
    private String password;
    private String institudeIsrael;
    private String institudeAbroad;
    private String degree;
    private String countryAbroad;

    public Student(String name, String email, String institudeIsrael, String institudeAbroad,
                   String degree, String countryAbroad) {
        this.name = name;
        this.email = email;
        this.institudeIsrael = institudeIsrael;
        this.institudeAbroad = institudeAbroad;
        this.degree = degree;
        this.countryAbroad = countryAbroad;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInstitudeIsrael() {
        return institudeIsrael;
    }

    public void setInstitudeIsrael(String institudeIsrael) {
        this.institudeIsrael = institudeIsrael;
    }

    public String getInstitudeAbroad() {
        return institudeAbroad;
    }

    public void setInstitudeAbroad(String institudeAbroad) {
        this.institudeAbroad = institudeAbroad;
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
