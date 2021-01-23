package ru.ifmo.rain.karimov.i18n.bundles;

import java.util.ListResourceBundle;

public class ResourceBundle_en extends ListResourceBundle {
    private final Object[][] CONTENTS = {
            {"analyzedFile", "analyzed file"},
            {"summaryStats", "summary statistics"},
            {"number", "number of"},
            {"uniques", "uniques"},
            {"statsOf", "statistics of"},
            {"minimum", "minimum"},
            {"maximum", "maximum"},
            {"minLength", "minimum length of"},
            {"maxLength", "maximum length of"},
            {"avgValue", "average value"},
            //count
            {"n_lines", "lines"},
            {"n_sentences", "sentences"},
            {"n_words", "words"},
            {"n_numbers", "numbers"},
            {"n_currencies", "currencies"},
            {"n_dates", "dates"},
            //head of stats
            {"st_lines", "lines"},
            {"st_sentences", "sentences"},
            {"st_words", "words"},
            {"st_numbers", "numbers"},
            {"st_currencies", "currencies"},
            {"st_dates", "dates"},
            //min/max value
            {"mm_lines", "lines"},
            {"mm_sentences", "sentences"},
            {"mm_words", "words"},
            {"mm_numbers", "numbers"},
            {"mm_currencies", "currencies"},
            {"mm_dates", "dates"},
            //min/max length
            {"ml_lines", "lines"},
            {"ml_sentences", "sentences"},
            {"ml_words", "words"},
            {"ml_numbers", "numbers"},
            {"ml_currencies", "currencies"},
            {"ml_dates", "dates"},
            //min/max length
            {"av_lines", "lines"},
            {"av_sentences", "sentences"},
            {"av_words", "words"},
            {"av_numbers", "numbers"},
            {"av_currencies", "currencies"},
            {"av_dates", "dates"},
    };

    @Override
    protected Object[][] getContents() {
        return CONTENTS;
    }
}
