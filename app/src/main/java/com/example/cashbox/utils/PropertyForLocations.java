package com.example.cashbox.utils;

public class PropertyForLocations {

    private PropLocations[] loc = new PropLocations[1];

    public void setValue(PropLocations[] loc){
        this.loc = loc;
    }

    public PropLocations[] getValue(){
        return loc;
    }


}

