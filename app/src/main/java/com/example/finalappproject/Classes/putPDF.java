package com.example.finalappproject.Classes;

public class putPDF {
    String name,url,courseName,degree,instituteAbroad,country;

    public putPDF() {
    }

    public putPDF(String name, String url,String courseName,String degree,String instituteAbroad,String country) {
        this.name = name;
        this.url = url;
        this.courseName = courseName;
        this.degree = degree;
        this.instituteAbroad = instituteAbroad;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getDegree() {
        return degree;
    }

    public String getInstituteAbroad() {
        return instituteAbroad;
    }

    public String getCountry() {
        return country;
    }
}
