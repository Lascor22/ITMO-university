package ru.itmo.translations.parser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Tree {
    private final String node;
    private final List<Tree> children;

    public String getNode() {
        return node;
    }

    public List<Tree> getChildren() {
        return children;
    }

    Tree(String node, Tree... children) {
        this.node = node;
        this.children = Arrays.asList(children);
    }

    Tree(String node) {
        this.node = node;
        children = Collections.emptyList();
    }

    @Override
    public String toString() {
        switch (node) {
            case "not":
                return node + " ";
            case "xor":
            case "or":
            case "and":
                return " " + node + " ";
            case "v":
            case "(":
            case ")":
                return node;
            default:
                return children.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(""));
        }
    }
}
