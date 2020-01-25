package com.example.cashbox;

public class Order {
    private String number, cashboxName, storeName, problemDesc;

    public Order(String number, String cashboxName, String storeName, String problemDesc) {
        this.number = number;
        this.cashboxName = cashboxName;
        this.storeName = storeName;
        this.problemDesc = problemDesc;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCashboxName() {
        return cashboxName;
    }

    public void setCashboxName(String cashboxName) {
        this.cashboxName = cashboxName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getProblemDesc() {
        return problemDesc;
    }

    public void setProblemDesc(String problemDesc) {
        this.problemDesc = problemDesc;
    }
}
