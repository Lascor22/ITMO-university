package ru.ifmo.rain.karimov.i18n.stats;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

public class NumericStats extends BaseStat {
    private Double sumItems;
    private final Map<String, Double> itemToValue;

    public NumericStats() {
        super();
        this.sumItems = 0.0;
        this.itemToValue = new LinkedHashMap<>();
    }

    @Override
    public void add(String item) {
        throw new UnsupportedOperationException();
    }

    public void add(String item, Number number) {
        sumItems += number.doubleValue();
        count++;
        itemToValue.putIfAbsent(item, number.doubleValue());
        maxLength = maxLength.length() < item.length() ? item : maxLength;
        minLength = minLength.isEmpty() || minLength.length() > item.length() ? item : minLength;
    }

    @Override
    public String getCommon() {
        Map.Entry<String, Double> entry = itemToValue.entrySet().stream().max(Comparator.comparingDouble(Map.Entry::getValue)).orElse(null);
        if (entry == null) {
            return "";
        }
        return entry.getKey();
    }

    @Override
    public int getUniqueCount() {
        return itemToValue.size();
    }

    @Override
    public String getUncommon() {
        Map.Entry<String, Double> entry = itemToValue.entrySet().stream().min(Comparator.comparingDouble(Map.Entry::getValue)).orElse(null);
        if (entry == null) {
            return "";
        }
        return entry.getKey();
    }

    @Override
    public Double getAvgValue() {
        return count != 0 ? sumItems / count : 0;
    }
}
