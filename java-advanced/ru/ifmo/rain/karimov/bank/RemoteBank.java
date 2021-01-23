package ru.ifmo.rain.karimov.bank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class RemoteBank extends UnicastRemoteObject implements Bank {
    private final int port;
    private final ConcurrentMap<String, Person> persons;
    private final ConcurrentMap<String, Account> accounts;
    private final ConcurrentMap<String, Set<String>> passportToAccounts;

    public RemoteBank(int port) throws RemoteException {
        super(port);
        persons = new ConcurrentHashMap<>();
        accounts = new ConcurrentHashMap<>();
        passportToAccounts = new ConcurrentHashMap<>();
        this.port = port;
    }

    @Override
    public Person getLocalPerson(String passport) throws RemoteException {
        if (passport == null) {
            return null;
        }

        Person person = persons.get(passport);
        if (person == null) {
            return null;
        }

        Set<String> accountNames = getPersonAccounts(person);
        Map<String, LocalAccount> accounts = new ConcurrentHashMap<>();
        for (String accountName : accountNames) {
            try {
                Account curRemote = getAccount(person, accountName);
                accounts.put(accountName, new LocalAccount(curRemote.getId(), curRemote.getAmount()));
            } catch (RemoteException e) {
                System.err.println("Error with creating local accounts. " + e);
            }
        }
        return new LocalPerson(person.getName(), person.getSurname(), person.getPassport(), accounts);
    }

    @Override
    public Person getRemotePerson(String passport) {
        if (passport == null) {
            return null;
        }
        return persons.get(passport);
    }

    @Override
    public Set<String> getPersonAccounts(Person person) throws RemoteException {
        if (person == null) {
            return null;
        }
        System.out.println("Retrieving person '" + person.getPassport() + "' accounts");
        if (person instanceof LocalPerson) {
            return ((LocalPerson) person).getAccounts();
        }
        return passportToAccounts.get(person.getPassport());
    }

    @Override
    public boolean createPerson(String name, String surname, String passport) throws RemoteException {
        if (name == null || surname == null || passport == null || persons.get(passport) != null) {
            return false;
        }
        System.out.println("Create person " + name + " " + surname + " with passport " + passport);
        persons.put(passport, new RemotePerson(name, surname, passport, port));
        passportToAccounts.put(passport, new ConcurrentSkipListSet<>());
        return true;
    }

    @Override
    public boolean checkPerson(String name, String surname, String passport) throws RemoteException {
        if (name == null || surname == null || passport == null) {
            return false;
        }

        System.out.println("Check " + name + " " + surname);
        Person person = persons.get(passport);
        return person != null && person.getName().equals(name) && person.getSurname().equals(surname);
    }

    @Override
    public boolean createAccount(Person person, String id) throws RemoteException {
        if (person == null || id == null) {
            return false;
        }

        String accountId = person.getPassport() + ":" + id;
        if (accounts.containsKey(accountId)) {
            return false;
        }

        System.out.println("Create account " + id + " with passport " + person.getPassport());
        Account account = new RemoteAccount(id, port);

        accounts.put(accountId, account);
        if (passportToAccounts.get(person.getPassport()) == null) {
            passportToAccounts.put(person.getPassport(), new ConcurrentSkipListSet<>());
        }
        passportToAccounts.get(person.getPassport()).add(id);

        return true;
    }

    public Account getAccount(Person person, String id) throws RemoteException {
        if (person == null || id == null) {
            return null;
        }

        String accountId = person.getPassport() + ":" + id;
        Account account = accounts.get(accountId);

        if (account == null)
            return null;

        System.out.println("Retrieving account " + accountId + " with value " + account.getAmount());

        if (person instanceof LocalPerson) {
            return ((LocalPerson) person).getAccount(id);
        }
        return account;
    }
}
