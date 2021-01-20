package ru.itmo.translations;

import ru.itmo.translations.exceptions.ParseException;
import ru.itmo.translations.parser.Parser;
import ru.itmo.translations.parser.Tree;

import java.io.ByteArrayInputStream;

public class Main {
    public static void main(String[] args) {
        try {
            String expression = "not (not v)";
            Tree tree = new Parser().parse(new ByteArrayInputStream(expression.getBytes()));
//            Visualizer.show(tree);
            System.out.println(tree);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            String expression = "not not v";
            Tree tree = new Parser().parse(new ByteArrayInputStream(expression.getBytes()));
//            Visualizer.show(tree);
            System.out.println(tree);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
