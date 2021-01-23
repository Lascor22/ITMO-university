package ru.ifmo.rain.karimov.i18n;

import ru.ifmo.rain.karimov.i18n.bundles.ResourceBundle_en;
import ru.ifmo.rain.karimov.i18n.bundles.ResourceBundle_ru;
import ru.ifmo.rain.karimov.i18n.stats.BaseStat;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

public class HTMLBuilder {
    private final Locale locale;
    private final ResourceBundle bundle;
    private final Map<String, BaseStat> stats;
    private final String fileName;
    private final NumberFormat numForm;

    private static final Map<Locale, String> outLocals;
    private static final Set<String> keys;

    static {
        outLocals = new LinkedHashMap<>();
        keys = new LinkedHashSet<>(List.of("lines", "sentences", "words", "numbers", "currencies", "dates"));
        outLocals.put(Locale.forLanguageTag("ru"), ResourceBundle_ru.class.getCanonicalName());
        outLocals.put(Locale.forLanguageTag("en"), ResourceBundle_en.class.getCanonicalName());
    }

    public HTMLBuilder(Locale locale, Map<String, BaseStat> stats, String fileName) {
        this.fileName = fileName;
        this.stats = stats;
        this.locale = locale;
        numForm = NumberFormat.getNumberInstance(locale);

        if (!outLocals.containsKey(locale)) {
            throw new IllformedLocaleException("locale " + locale + "unsupported");
        }
        bundle = ResourceBundle.getBundle(outLocals.get(locale));

    }

    public String buildHTML() {
        List<String> result = new ArrayList<>();
        result.add(createHead());
        keys.forEach(key -> {
            if (stats.get(key).getCount() != 0) {
                result.add(createStat(key));
            }
        });
        result.add(END_TEMPLATE);
        return result.stream().collect(Collectors.joining(System.lineSeparator()));
    }

    private String format(String template, Object... args) {
        return String.format(locale, template, args);
    }

    private String createHead() {
        List<Object> args = new ArrayList<>();
        args.add(format("%s : %s", bundle.getString("analyzedFile"), fileName));
        args.add(bundle.getString("summaryStats"));
        args.addAll(keys.stream().map(this::createNumber).collect(Collectors.toList()));
        return format(HEAD_TEMPLATE, args.toArray());
    }

    private String createStat(String type) {
        return format(STAT_TEMPLATE,
                format("%s %s", bundle.getString("statsOf"),
                        bundle.getString("st_".concat(type))),
                format("%s (%s %s)",
                        createNumber(type),
                        numForm.format(stats.get(type).getUniqueCount()),
                        bundle.getString("uniques")),
                format("%s %s: %s",
                        bundle.getString("minimum"),
                        bundle.getString("mm_".concat(type)),
                        stats.get(type).getUncommon()),
                format("%s %s: %s",
                        bundle.getString("maximum"),
                        bundle.getString("mm_".concat(type)),
                        stats.get(type).getCommon()),
                format("%s %s: %s (%s)",
                        bundle.getString("minLength"),
                        bundle.getString("ml_".concat(type)),
                        numForm.format(stats.get(type).getMinLength().length()), stats.get(type).getMinLength()),
                format("%s %s: %s (%s)",
                        bundle.getString("maxLength"),
                        bundle.getString("ml_".concat(type)),
                        numForm.format(stats.get(type).getMaxLength().length()), stats.get(type).getMaxLength()),
                format("%s %s: %s",
                        bundle.getString("avgValue"),
                        bundle.getString("av_".concat(type)),
                        formatAvg(type)));
    }

    private String formatAvg(String type) {
        if ("dates".equals(type)) {
            DateFormat.getDateInstance(DateFormat.DEFAULT, locale).format(stats.get(type).getAvgValue());
        }
        return numForm.format(stats.get(type).getAvgValue());
    }

    private String createNumber(String type) {
        return format("%s %s: %s",
                bundle.getString("number"),
                bundle.getString("n_".concat(type)),
                numForm.format(stats.get(type).getCount()));
    }


    private static final String STAT_TEMPLATE = Arrays.stream(new String[]{
            "<h3>%s</h3>",
            "<p>%s</p>",
            "<p>%s</p>",
            "<p>%s</p>",
            "<p>%s</p>",
            "<p>%s</p>",
            "<p>%s</p>"}).collect(Collectors.joining(System.lineSeparator()));

    private static final String HEAD_TEMPLATE = Arrays.stream(new String[]{
            "<html>",
            "<head>",
            "    <META http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">",
            "</head>",
            "",
            "<h2>%s</h2>",
            "<h3>%s</h3>",
            "<p>%s</p>",
            "<p>%s</p>",
            "<p>%s</p>",
            "<p>%s</p>",
            "<p>%s</p>",
            "<p>%s</p>"}).collect(Collectors.joining(System.lineSeparator()));

    private static final String END_TEMPLATE = "</html>";
}
