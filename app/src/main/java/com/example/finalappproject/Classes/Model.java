package com.example.finalappproject.Classes;

public class Model {
    String imageUrl,description;

    public Model() {
    }
    public Model(String imageUrl,String description) {
        this.imageUrl = imageUrl;
        this.description = description;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
