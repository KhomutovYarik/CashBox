package com.example.cashbox.utils;

import com.google.gson.annotations.SerializedName;

public class Property {

    @SerializedName("value")
    private String value;

    public void setValue(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }


}
