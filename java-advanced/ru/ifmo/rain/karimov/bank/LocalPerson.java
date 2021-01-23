package ru.ifmo.rain.karimov.bank;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public class LocalPerson extends BasePerson implements Serializable {
    private final Map<String, LocalAccount> accounts;

    LocalPerson(String name, String surname, String passport, Map<String, LocalAccount> accounts) {
        super(name, surname, passport);
        this.accounts = accounts;
    }

    Set<String> getAccounts() {
        return accounts.keySet();
    }

    Account getAccount(String accountId) {
        return accounts.get(accountId);
    }
}
