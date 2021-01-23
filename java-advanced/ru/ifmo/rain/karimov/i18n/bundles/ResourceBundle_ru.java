package ru.ifmo.rain.karimov.i18n.bundles;

import java.util.ListResourceBundle;

public class ResourceBundle_ru extends ListResourceBundle {
    private final Object[][] CONTENTS = {
            {"analyzedFile", "анализируемый файл"},
            {"summaryStats", "сводная статистика"},
            {"number", "число"},
            {"uniques", "уникальных"},
            {"statsOf", "статистика по"},
            {"minimum", "Минимальное"},
            {"maximum", "Максимальное"},
            {"minLength", "Минимальная длина"},
            {"maxLength", "Максимальная длина"},
            {"avgValue", "Среднее значение"},
            //count
            {"n_lines", "строк"},
            {"n_sentences", "предложений"},
            {"n_words", "слов"},
            {"n_numbers", "чисел"},
            {"n_currencies", "валют"},
            {"n_dates", "дат"},
            //head of stats
            {"st_lines", "строкам"},
            {"st_sentences", "предложениям"},
            {"st_words", "словам"},
            {"st_numbers", "числам"},
            {"st_currencies", "валютам"},
            {"st_dates", "датам"},
            //min/max value
            {"mm_lines", "строка"},
            {"mm_sentences", "предложение"},
            {"mm_words", "слово"},
            {"mm_numbers", "число"},
            {"mm_currencies", "валюта"},
            {"mm_dates", "дата"},
            //min/max length
            {"ml_lines", "строки"},
            {"ml_sentences", "предложения"},
            {"ml_words", "слова"},
            {"ml_numbers", "числа"},
            {"ml_currencies", "валюты"},
            {"ml_dates", "даты"},
            //avg value
            {"av_lines", "строки"},
            {"av_sentences", "предложения"},
            {"av_words", "слова"},
            {"av_numbers", "числа"},
            {"av_currencies", "валюты"},
            {"av_dates", "даты"},
    };

    @Override
    protected Object[][] getContents() {
        return CONTENTS;
    }
}
