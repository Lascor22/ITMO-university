package ru.ifmo.rain.karimov.i18n.stats;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

public class LexicalStats extends BaseStat {
    private int sumItems;
    private final Map<String, Integer> itemsToCount;

    public LexicalStats() {
        super();
        this.sumItems = 0;
        this.itemsToCount = new LinkedHashMap<>();
    }

    @Override
    public void add(String item) {
        sumItems += item.length();
        count++;
        itemsToCount.computeIfPresent(item, (k, v) -> v + 1);
        itemsToCount.putIfAbsent(item, 1);
        maxLength = maxLength.length() < item.length() ? item : maxLength;
        minLength = minLength.isEmpty() || minLength.length() > item.length() ? item : minLength;
    }

    @Override
    public int getUniqueCount() {
        return itemsToCount.size();
    }

    @Override
    public String getCommon() {
        Map.Entry<String, Integer> entry = itemsToCount.entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue)).orElse(null);
        if (entry == null) {
            return "";
        }
        return entry.getKey();
    }

    @Override
    public String getUncommon() {
        Map.Entry<String, Integer> entry = itemsToCount.entrySet().stream().min(Comparator.comparingInt(Map.Entry::getValue)).orElse(null);
        if (entry == null) {
            return "";
        }
        return entry.getKey();
    }

    @Override
    public Integer getAvgValue() {
        return sumItems / count;
    }
}
