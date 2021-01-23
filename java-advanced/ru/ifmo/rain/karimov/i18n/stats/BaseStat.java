package ru.ifmo.rain.karimov.i18n.stats;

public abstract class BaseStat {
    protected int count;
    protected String minLength;
    protected String maxLength;

    public BaseStat() {
        minLength = "";
        maxLength = "";
        count = 0;
    }

    public abstract void add(String item);

    public int getCount() {
        return count;
    }

    public abstract int getUniqueCount();

    public abstract String getCommon();

    public abstract String getUncommon();

    public String getMinLength() {
        return minLength;
    }

    public String getMaxLength() {
        return maxLength;
    }

    public abstract <T> T getAvgValue();
}
