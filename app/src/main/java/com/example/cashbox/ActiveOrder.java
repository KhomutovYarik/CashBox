package com.example.cashbox;

public class ActiveOrder {
    private String number, cashboxName, storeName, problemDesc, offersNumber, minPrice;

    public ActiveOrder(String number, String cashboxName, String storeName, String problemDesc, String offersNumber, String minPrice) {
        this.number = number;
        this.cashboxName = cashboxName;
        this.storeName = storeName;
        this.problemDesc = problemDesc;
        this.offersNumber = offersNumber;
        this.minPrice = minPrice;
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

    public String getOffersNumber() {
        return offersNumber;
    }

    public void setOffersNumber(String offersNumber) {
        this.offersNumber = offersNumber;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }
}
