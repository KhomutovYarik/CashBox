package com.example.cashbox;

public class Cashbox
{
    private String name, model, serialNumber;

    public Cashbox(String name, String model, String serialNumber) {
        this.name = name;
        this.model = model;
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
