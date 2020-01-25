package com.example.cashbox;

public class ActiveOrder extends Order {
    private String offersNumber, minPrice;

    public ActiveOrder(String number, String cashboxName, String storeName, String problemDesc, String offersNumber, String minPrice) {
        super(number, cashboxName, storeName, problemDesc);
        this.offersNumber = offersNumber;
        this.minPrice = minPrice;
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
