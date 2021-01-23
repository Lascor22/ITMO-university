package ru.ifmo.rain.karimov.bank;

import java.io.Serializable;

public class LocalAccount extends BaseAccount implements Serializable {

    public LocalAccount(String id, int amount) {
        super(id, amount);
    }
}
