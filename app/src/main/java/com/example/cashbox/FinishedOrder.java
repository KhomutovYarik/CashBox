package com.example.cashbox;

public class FinishedOrder extends Order{
    private int rating;
    private boolean status;

    public FinishedOrder(String number, String cashboxName, String storeName, String problemDesc, int rating, boolean status) {
        super(number, cashboxName, storeName, problemDesc);
        this.rating = rating;
        this.status = status;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
