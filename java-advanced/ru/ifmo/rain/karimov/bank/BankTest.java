package ru.ifmo.rain.karimov.bank;

import org.junit.Test;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import java.util.Set;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class BankTest {

    private static Bank bank;

    private static final int PEOPLE_COUNT = 201;

    @org.junit.BeforeClass
    public static void beforeClass() throws Exception {
        final Registry registry = LocateRegistry.createRegistry(8889);

        registry.rebind("//localhost/bank", new RemoteBank(8888));
        bank = (Bank) registry.lookup("//localhost/bank");

        System.out.println("Bank created");
    }

    @Test
    public void getPerson() throws RemoteException {
        assertNull(bank.getLocalPerson("" + (-PEOPLE_COUNT)));
        assertNull(bank.getRemotePerson("" + (-PEOPLE_COUNT)));
        for (int i = 0; i < PEOPLE_COUNT; ++i) {
            bank.createPerson("getPersonTest" + i, "", "getPersonTest" + i);
            Person remotePerson = bank.getRemotePerson("getPersonTest" + i);
            assertEquals("getPersonTest" + i, remotePerson.getName());
            assertEquals("", remotePerson.getSurname());
            assertEquals("getPersonTest" + i, remotePerson.getPassport());

            Person localPerson = bank.getLocalPerson("getPersonTest" + i);
            assertEquals("getPersonTest" + i, localPerson.getName());
            assertEquals("", localPerson.getSurname());
            assertEquals("getPersonTest" + i, localPerson.getPassport());

        }
    }

    @Test
    public void getAccountId() throws RemoteException {
        Random random = new Random();
        for (int i = 0; i < PEOPLE_COUNT; ++i) {
            assertTrue(bank.createPerson("getAccountsIdTest", "" + i, "getAccountsIdTest" + i));

            int operations = random.nextInt(PEOPLE_COUNT);
            int accCount = 0;
            Person remote = bank.getRemotePerson("getAccountsIdTest" + i);
            for (int j = 0; j < operations; j++) {
                if (bank.createAccount(remote, "" + random.nextInt())) {
                    accCount++;
                }
            }
            Set<String> accountsId = bank.getPersonAccounts(remote);
            assertNotNull(accountsId);
            assertEquals(accCount, accountsId.size());
        }
    }

    @Test
    public void checkAndCreatePerson() throws RemoteException {
        for (int i = 0; i < PEOPLE_COUNT; ++i) {
            assertFalse(bank.checkPerson("checkPersonTest" + i, "", "checkPersonTest" + i));
            assertTrue(bank.createPerson("checkPersonTest" + i, "", "checkPersonTest" + i));
            assertTrue(bank.checkPerson("checkPersonTest" + i, "", "checkPersonTest" + i));
        }
    }

    @Test
    public void checkRemoteAfterLocal() throws RemoteException {
        String passport = "checkRemoteAfterLocalTest1";
        assertTrue(bank.createPerson("checkRemoteAfterLocalTest", "1", passport));

        Person remotePerson = bank.getRemotePerson(passport);
        assertNotNull(remotePerson);

        assertTrue(bank.createAccount(remotePerson, "1"));
        Person localPerson = bank.getLocalPerson(passport);
        assertNotNull(localPerson);

        Account localAccount = bank.getAccount(localPerson, "1");
        localAccount.setAmount(localAccount.getAmount() + 100);

        Account remoteAccount = bank.getAccount(remotePerson, "1");
        assertEquals(100, localAccount.getAmount());
        assertEquals(0, remoteAccount.getAmount());
    }

    @Test
    public void checkLocalAfterRemote() throws RemoteException {
        String passport = "CheckLocalAfterRemoteTest1";
        bank.createPerson("checkLocalAfterRemoteTest", "1", passport);
        Person remotePerson = bank.getRemotePerson(passport);

        assertNotNull(remotePerson);

        assertTrue(bank.createAccount(remotePerson, "1"));
        Account remoteAccount = bank.getAccount(remotePerson, "1");

        Person localPerson2 = bank.getLocalPerson(passport);

        remoteAccount.setAmount(remoteAccount.getAmount() + 100);

        Person localPerson = bank.getLocalPerson(passport);
        assertNotNull(localPerson);

        Account localAccount = bank.getAccount(localPerson, "1");
        Account localAccount2 = bank.getAccount(localPerson2, "1");

        assertEquals(localAccount.getAmount(), remoteAccount.getAmount());
        assertEquals(localAccount2.getAmount() + 100, localAccount.getAmount());
    }

    @Test
    public void checkCreatingAccount() throws RemoteException {
        String passport = "checkCreatingAccountTest1";
        bank.createPerson("checkCreatingAccountTest", "1", passport);
        Person remote = bank.getRemotePerson(passport);
        Person local = bank.getLocalPerson(passport);

        bank.createAccount(remote, "2");
        assertNull(bank.getAccount(local, "2"));
        assertEquals(1, bank.getPersonAccounts(remote).size());
        assertNotNull(bank.getAccount(remote, "2"));
        assertNotEquals(bank.getPersonAccounts(local), bank.getPersonAccounts(remote));
    }

    @Test
    public void checkRemoteRemote() throws RemoteException {
        String passport = "checkRemoteRemoteTest1";
        bank.createPerson("checkRemoteRemoteTest", "1", passport);

        Person remote1 = bank.getRemotePerson(passport);
        Person remote2 = bank.getRemotePerson(passport);

        bank.createAccount(remote1, "1");
        bank.createAccount(remote2, "2");

        assertEquals(2, bank.getPersonAccounts(remote1).size());
        assertEquals(bank.getPersonAccounts(remote1).size(), bank.getPersonAccounts(remote2).size());
    }

    @Test
    public void checkLocalLocal() throws RemoteException {
        String passport = "checkLocalLocalTest1";
        bank.createPerson("checkLocalLocalTest", "1", passport);

        Person local1 = bank.getLocalPerson(passport);
        Person local2 = bank.getLocalPerson(passport);

        bank.createAccount(local1, "1");
        bank.createAccount(local2, "2");

        Person local3 = bank.getLocalPerson(passport);
        assertEquals(2, bank.getPersonAccounts(local3).size());
        assertEquals(0, bank.getPersonAccounts(local1).size());
        assertEquals(bank.getPersonAccounts(local1).size(), bank.getPersonAccounts(local2).size());
    }
}
