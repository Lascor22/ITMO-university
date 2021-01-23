package ru.ifmo.rain.karimov.bank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemotePerson extends BasePerson {
    public RemotePerson(String name, String surname, String passport, int port) throws RemoteException {
        super(name, surname, passport);
        UnicastRemoteObject.exportObject(this, port);
    }
}
