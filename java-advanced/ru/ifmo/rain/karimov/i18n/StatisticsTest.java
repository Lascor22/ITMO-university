package ru.ifmo.rain.karimov.i18n;

import org.junit.BeforeClass;
import org.junit.Test;
import ru.ifmo.rain.karimov.i18n.stats.BaseStat;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class StatisticsTest {
    private static Map<String, Locale> locals;
    private static Map<String, Path> paths;
    private static final Path TEXTS_PATH = Path.of("ru", "ifmo", "rain", "karimov", "i18n", "texts");

    @BeforeClass
    public static void beforeClass() {
        locals = new LinkedHashMap<>();
        locals.put("en", Locale.forLanguageTag("en"));
        locals.put("ru", Locale.forLanguageTag("ru"));
        locals.put("bn", Locale.forLanguageTag("bn"));
        locals.put("ar", Locale.forLanguageTag("ar"));
        locals.put("si", Locale.forLanguageTag("si"));
        locals.put("hi", Locale.forLanguageTag("hi"));
        locals.put("th", Locale.forLanguageTag("th"));
        locals.put("ch", Locale.forLanguageTag("zh-Hans-SG"));

        paths = new LinkedHashMap<>();
        paths.put("en", TEXTS_PATH.resolve("en.txt"));
        paths.put("ru", TEXTS_PATH.resolve("ru.txt"));
        paths.put("ar", TEXTS_PATH.resolve("ar.txt"));
        paths.put("bn", TEXTS_PATH.resolve("bn.txt"));
        paths.put("ch", TEXTS_PATH.resolve("ch.txt"));
        paths.put("hi", TEXTS_PATH.resolve("hi.txt"));
        paths.put("si", TEXTS_PATH.resolve("si.txt"));
        paths.put("th", TEXTS_PATH.resolve("th.txt"));
    }

    @Test
    public void TestEnglish() {
        Map<String, BaseStat> stats = getMap("en", "english");
        if (stats == null) {
            return;
        }
        assertEquals(19, stats.get("lines").getCount());
        assertEquals(26, stats.get("sentences").getUniqueCount());
        assertEquals(1, stats.get("words").getMinLength().length());
        assertEquals(7, stats.get("numbers").getMaxLength().length());
    }

    @Test
    public void TestRussian() {
        Map<String, BaseStat> stats = getMap("ru", "russian");
        if (stats == null) {
            return;
        }
        assertEquals(3, stats.get("numbers").getCount());
        assertEquals(188, stats.get("lines").getUniqueCount());
        assertEquals(4, (int) stats.get("words").getAvgValue());
        assertEquals(61, stats.get("sentences").getMinLength().length());
    }

    @Test
    public void TestThai() {
        Map<String, BaseStat> stats = getMap("th", "thai");
        if (stats == null) {
            return;
        }
        assertEquals(0, stats.get("dates").getCount());
        assertEquals(4, stats.get("numbers").getUniqueCount());
        assertEquals(715.75, stats.get("numbers").getAvgValue());
        assertEquals(16, stats.get("words").getMaxLength().length());
    }

    @Test
    public void TestHindi() {
        Map<String, BaseStat> stats = getMap("hi", "hindi");
        if (stats == null) {
            return;
        }
        assertEquals(47, stats.get("words").getCount());
        assertEquals(Integer.toString(3), stats.get("numbers").getUncommon());
        assertEquals(298, (int) stats.get("sentences").getAvgValue());
        assertEquals(296, stats.get("lines").getMaxLength().length());
    }

    @Test
    public void TestSenegal() {
        Map<String, BaseStat> stats = getMap("si", "senegal");
        if (stats == null) {
            return;
        }
        assertEquals(493, stats.get("words").getCount());
        assertEquals(0, stats.get("currencies").getUniqueCount());
        assertEquals(301, (int) stats.get("lines").getAvgValue());
        assertEquals(Integer.toString(19), stats.get("numbers").getCommon());
    }

    @Test
    public void TestArabian() {
        Map<String, BaseStat> stats = getMap("ar", "arabian");
        if (stats == null) {
            return;
        }
        assertEquals(1, stats.get("lines").getCount());
        assertEquals(3, stats.get("numbers").getUniqueCount());
        assertEquals(67, stats.get("words").getMaxLength().length());
        assertEquals(108, (int) stats.get("sentences").getAvgValue());
    }

    @Test
    public void TestBengali() {
        Map<String, BaseStat> stats = getMap("bn", "bengali");
        if (stats == null) {
            return;
        }
        assertEquals(106, stats.get("words").getCount());
        assertEquals(2, stats.get("numbers").getUniqueCount());
        assertEquals(235, stats.get("sentences").getMaxLength().length());
        assertEquals(209, (int) stats.get("lines").getAvgValue());
    }

    @Test
    public void TestChinese() {
        Map<String, BaseStat> stats = getMap("ch", "chinese");
        if (stats == null) {
            return;
        }
        assertEquals(3, stats.get("numbers").getCount());
        assertEquals(5, stats.get("lines").getUniqueCount());
        assertEquals(130, stats.get("sentences").getMaxLength().length());
        assertEquals(3, (int) stats.get("words").getAvgValue());
    }

    private static Map<String, BaseStat> getMap(String key, String languageName) {
        Map<String, BaseStat> stats;
        try {
            stats = new TextStatistics(TextStatistics.readAll(paths.get(key)), locals.get(key)).calculateStats();
        } catch (IOException e) {
            System.err.println(
                    String.format("Test %s failed. Error while read text from file ", languageName) + e.getMessage());
        }
        return null;
    }
}
