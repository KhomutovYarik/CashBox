package com.example.cashbox.utils;

import com.google.gson.annotations.SerializedName;

public class PropLocations {

    @SerializedName("region")
    private String region;

    public void setValue(String region){
        this.region = region;
    }

    public String getValue(){
        return region;
    }
}