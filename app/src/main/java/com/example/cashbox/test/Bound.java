package com.example.cashbox.test;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bound {

    @SerializedName("value")
    @Expose
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
