package ru.ifmo.rain.karimov.i18n.stats;

import java.time.Instant;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class DateStats extends BaseStat {
    private Long sumItems;
    private final Map<String, Date> itemToValue;

    public DateStats() {
        super();
        this.sumItems = 0L;
        this.itemToValue = new LinkedHashMap<>();
    }

    @Override
    public void add(String item) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getUniqueCount() {
        return itemToValue.size();
    }

    public void add(String item, Date date) {
        sumItems += date.getTime();
        count++;
        itemToValue.putIfAbsent(item, date);
        maxLength = maxLength.length() < item.length() ? item : maxLength;
        minLength = minLength.isEmpty() || minLength.length() > item.length() ? item : minLength;
    }

    @Override
    public String getCommon() {
        Map.Entry<String, Date> entry = itemToValue.entrySet().stream().max(Map.Entry.comparingByValue()).orElse(null);
        if (entry == null) {
            return "";
        }
        return entry.getKey();
    }

    @Override
    public String getUncommon() {
        Map.Entry<String, Date> entry = itemToValue.entrySet().stream().min(Map.Entry.comparingByValue()).orElse(null);
        if (entry == null) {
            return "";
        }
        return entry.getKey();
    }

    @Override
    public Date getAvgValue() {
        return count == 0 ? null : Date.from(Instant.ofEpochMilli(sumItems / count));
    }
}
