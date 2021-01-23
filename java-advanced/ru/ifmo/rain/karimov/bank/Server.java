package ru.ifmo.rain.karimov.bank;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class Server {
    private final static int PORT = 8888;

    public static void main(final String... args) {
        try {
            final Bank bank = new RemoteBank(PORT);
            Naming.rebind("//localhost/bank", bank);
        } catch (RemoteException e) {
            System.err.println("Cannot export object: " + e.getMessage());
            e.printStackTrace();
        } catch (MalformedURLException e) {
            System.err.println("Malformed URL");
        }
        System.out.println("Server started");
    }
}
