package ru.ifmo.rain.karimov.bank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteAccount extends BaseAccount {
    RemoteAccount(String id, int port) throws RemoteException {
        super(id, 0);
        UnicastRemoteObject.exportObject(this, port);
    }
}
