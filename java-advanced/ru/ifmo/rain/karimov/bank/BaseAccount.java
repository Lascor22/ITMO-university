package ru.ifmo.rain.karimov.bank;

public class BaseAccount implements Account {
    private final String id;
    private int amount;

    public BaseAccount(String id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public int getAmount() {
        System.out.println("Get amount for account " + id);
        return amount;
    }

    @Override
    public void setAmount(int amount) {
        System.out.println("Set amount for account " + id);
        this.amount = amount;
    }
}
