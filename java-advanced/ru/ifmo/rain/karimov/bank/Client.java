package ru.ifmo.rain.karimov.bank;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {
    public static void main(final String... args) {
        try {
            final Bank bank;
            try {
                bank = (Bank) Naming.lookup("//localhost/bank");
            } catch (NotBoundException e) {
                System.err.println("Bank is not bound");
                return;
            } catch (MalformedURLException e) {
                System.err.println("Bank URL is invalid");
                return;
            }

            String name, surname, passport, accountId;
            int change;

            try {
                name = args[0];
                surname = args[1];
                passport = args[2];
                accountId = args[3];
                change = Integer.parseInt(args[4]);
            } catch (NullPointerException | NumberFormatException e) {
                System.err.println("Wrong arguments. Need <name> <surname> <passport> <account id> <change>");
                return;
            }

            Person person = bank.getRemotePerson(passport);
            if (person == null) {
                System.out.println("Creating new person");
                bank.createPerson(name, surname, passport);
                person = bank.getRemotePerson(passport);
            }

            if (!bank.getPersonAccounts(person).contains(accountId)) {
                System.out.println("Creating account");
                Account account = bank.getAccount(person, accountId);
                if (account != null) {
                    System.err.println("Account already exists for another person.");
                    return;
                }
                bank.createAccount(person, accountId);
            }

            Account account = bank.getAccount(person, accountId);
            System.out.println("Account id: " + account.getId());
            System.out.println("Money: " + account.getAmount());
            System.out.println("Change money");
            account.setAmount(account.getAmount() + change);
            System.out.println("Money: " + account.getAmount());

        } catch (RemoteException e) {
            System.err.println("Problems with remote." + e);
        }
    }
}
