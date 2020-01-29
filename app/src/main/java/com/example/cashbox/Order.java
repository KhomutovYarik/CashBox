package com.example.cashbox;

public class Order {
    private String id, number, cashboxName, storeName, problem, problemDesc, status;

    public Order(String id, String number, String cashboxName, String storeName, String problem, String problemDesc, String status) {
        this.id = id;
        this.number = number;
        this.status = status;
        this.cashboxName = cashboxName;
        this.storeName = storeName;
        this.problem = problem;
        this.problemDesc = problemDesc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }
}
