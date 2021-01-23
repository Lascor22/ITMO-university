package ru.ifmo.rain.karimov.bank;

public class BasePerson implements Person {
    private final String name;
    private final String surname;
    private final String passport;

    public BasePerson(String name, String surname, String passport) {
        this.name = name;
        this.surname = surname;
        this.passport = passport;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public String getPassport() {
        return passport;
    }
}
