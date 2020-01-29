package com.example.cashbox;

public class FinishedOrder extends Order{
    private int rating;
    private boolean status;

    public FinishedOrder(String id, String number, String cashboxName, String storeName, String problem, String problemDesc, String Status, int rating) {
        super(id, number, cashboxName, storeName, problem, problemDesc, Status);
        this.rating = rating;
        this.status = status;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
