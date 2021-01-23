package ru.ifmo.rain.karimov.i18n;

import ru.ifmo.rain.karimov.i18n.stats.BaseStat;
import ru.ifmo.rain.karimov.i18n.stats.DateStats;
import ru.ifmo.rain.karimov.i18n.stats.LexicalStats;
import ru.ifmo.rain.karimov.i18n.stats.NumericStats;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.BreakIterator;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class TextStatistics {
    private final String text;
    private final Locale locale;

    public TextStatistics(String text, Locale locale) {
        this.text = text;
        this.locale = locale;
    }

    public Map<String, BaseStat> calculateStats() {
        Map<String, BaseStat> result = new LinkedHashMap<>();
        result.put("words", getLexicalStats(BreakIterator.getWordInstance(locale)));
        result.put("lines", getLineStats());
        result.put("sentences", getLexicalStats(BreakIterator.getSentenceInstance(locale)));
        result.put("dates", getStatsDate());
        getStatsNum(result);
        return result;
    }

    private LexicalStats getLineStats() {
        LexicalStats stats = new LexicalStats();
        text.lines().forEach(stats::add);
        return stats;
    }

    private void getStatsNum(Map<String, BaseStat> result) {
        BreakIterator boundary = BreakIterator.getWordInstance(locale);

        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);

        NumericStats numbers = new NumericStats();
        NumericStats currencies = new NumericStats();

        boundary.setText(text);
        process(boundary, word -> {
            Number number;
            try {
                number = currencyFormat.parse(word);
            } catch (ParseException e) {
                try {
                    number = numberFormat.parse(word);
                } catch (ParseException ignored) {
                    return;
                }
                numbers.add(word, number);
                return;
            }
            currencies.add(word, number);
        });

        result.put("numbers", numbers);
        result.put("currencies", currencies);
    }

    private DateStats getStatsDate() {
        BreakIterator boundary = BreakIterator.getWordInstance(locale);
        DateStats dateStats = new DateStats();
        boundary.setText(text);
        Set<DateFormat> formats = new LinkedHashSet<>(List.of(DateFormat.getDateInstance(DateFormat.SHORT, locale),
                DateFormat.getDateInstance(DateFormat.MEDIUM, locale),
                DateFormat.getDateInstance(DateFormat.LONG, locale),
                DateFormat.getDateInstance(DateFormat.FULL, locale)));

        process(boundary, word -> {
            Date date;
            for (DateFormat format : formats) {
                try {
                    date = format.parse(word);
                    dateStats.add(word, date);
                    return;
                } catch (ParseException ignored) {
                }
            }
        });
        return dateStats;
    }

    private LexicalStats getLexicalStats(BreakIterator boundary) {
        LexicalStats words = new LexicalStats();
        boundary.setText(text);
        process(boundary, words::add);
        return words;
    }

    private void process(BreakIterator boundary, final Consumer<? super String> consumer) {
        int start = boundary.first();
        for (int end = boundary.next(); end != BreakIterator.DONE; start = end, end = boundary.next()) {
            String item = text.substring(start, end);
            if (!item.isBlank()) {
                consumer.accept(text.substring(start, end));
            }
        }
    }

    static String readAll(Path path) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (Stream<String> stream = Files.lines(path, StandardCharsets.UTF_8)) {
            stream.forEach(s -> sb.append(s).append(System.lineSeparator()));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        if (args == null || args.length != 4) {
            System.out.println("Wrong input. Need <locate text> <locate out> <input file> <output file>");
            return;
        }
        Locale inputLocale = Locale.forLanguageTag(args[0]);
        Locale outputLocale = Locale.forLanguageTag(args[1]);
        Path inputPath = Path.of(args[2]);
        Path outputPath = Path.of(args[3]);
        Map<String, BaseStat> stats;
        try {
            stats = new TextStatistics(readAll(inputPath), inputLocale).calculateStats();
        } catch (IOException e) {
            System.err.println("Error while read input");
            return;
        }
        try (BufferedWriter writer = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8)) {
            writer.write(new HTMLBuilder(outputLocale, stats, args[2]).buildHTML());
        } catch (IOException e) {
            System.err.println("Error while write output " + e.getMessage());
        }
    }

}
