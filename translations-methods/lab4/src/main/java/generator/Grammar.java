package generator;

import generator.domain.*;

import java.util.*;

public class Grammar {
    private String name;
    private String startState;
    private final Map<String, TokenItem> tokenItems = new HashMap<>();
    private final Map<String, State> states = new HashMap<>();
    private final List<TokenItem> tokens = new ArrayList<>();
    private final List<TokenItem> skipTokens = new ArrayList<>();
    private final List<String> imports = new ArrayList<>();

    public void addImport(String s) {
        imports.add(s);
    }

    public void addToken(String tokenName, String pattern) {
        TokenItem item = new TokenItem(tokenName, pattern);
        tokens.add(item);
        if (tokenItems.containsKey(tokenName)) {
            System.err.println("Duplicate token name.");
            System.exit(-1);
        }
        tokenItems.put(tokenName, item);
    }

    public void addSkipToken(String tokenName, String pattern) {
        TokenItem item = new TokenItem(tokenName, pattern);
        skipTokens.add(item);
        if (tokenItems.containsKey(tokenName)) {
            System.err.println("Duplicate token name.");
            System.exit(-1);
        }
        tokenItems.put(tokenName, item);
    }

    public void addState(State s) {
        states.put(s.getName(), s);
    }

    public void createFirst() {
        boolean changed = true;
        while (changed) {
            changed = false;
            for (State state : states.values()) {
                for (Rule rule : state.getRules()) {
                    for (String item : rule.getItems()) {
                        if (item.equals("EPS")) {
                            if (state.addToFirst(item)) {
                                changed = true;
                            }
                        } else if (tokenItems.containsKey(item)) {
                            if (state.addToFirst(item)) {
                                changed = true;
                            }
                            break;
                        } else if (states.containsKey(item)) {
                            State st = states.get(item);
                            for (String newItem : st.getFirst()) {
                                if (state.addToFirst(newItem)) {
                                    changed = true;
                                }
                            }
                            if (st.noEpsilon()) {
                                break;
                            }
                        } else {
                            System.err.println("Unknown state name in " + state.getName() + ".");
                            System.exit(-1);
                        }
                    }
                }
            }
        }
    }

    public void createFollow() {
        State start = states.get(startState);
        start.addToFollow("_END");
        boolean changed = true;
        while (changed) {
            changed = false;
            for (State state : states.values()) {
                for (Rule rule : state.getRules()) {
                    Set<String> curGammaFirst = new HashSet<>();
                    curGammaFirst.add("_END");
                    for (int i = rule.getItems().size() - 1; i >= 0; i--) {
                        String item = rule.getItems().get(i);
                        if (item.equals("EPS")) {
                            curGammaFirst.add("EPS");
                            continue;
                        }
                        if (tokenItems.containsKey(item)) {
                            curGammaFirst.clear();
                            curGammaFirst.add(item);
                            continue;
                        }
                        if (states.containsKey(item)) {
                            State st = states.get(item);
                            for (String s : curGammaFirst) {
                                if (!s.equals("EPS")) {
                                    if (st.addToFollow(s)) {
                                        changed = true;
                                    }
                                }
                            }
                            if (curGammaFirst.contains("_END")) {
                                for (String s : state.getFollow()) {
                                    if (st.addToFollow(s)) {
                                        changed = true;
                                    }
                                }
                            }
                            if (st.noEpsilon()) {
                                curGammaFirst.clear();
                            }
                            curGammaFirst.addAll(st.getFirst());
                        } else {
                            System.err.println("Unknown state name " + item + " in " + state.getName() + ".");
                            System.exit(-1);
                        }
                    }
                }
            }
        }
    }

    public HashSet<String> firstForRule(Rule r) {
        HashSet<String> first = new HashSet<>();
        for (String item : r.getItems()) {
            if (item.equals("EPS")) {
                first.add("EPS");
            } else if (tokenItems.containsKey(item)) {
                first.add(item);
                break;
            } else if (states.containsKey(item)) {
                first.addAll(states.get(item).getFirst());
                if (!first.contains("EPS")) {
                    break;
                }
            }
        }
        return first;
    }

    public void setName(String grammarName) {
        this.name = grammarName;
    }

    public void setStartState(String startState) {
        this.startState = startState;
    }

    public Map<String, TokenItem> getTokenItems() {
        return tokenItems;
    }

    public Map<String, State> getStates() {
        return states;
    }

    public List<TokenItem> getTokens() {
        return tokens;
    }

    public List<TokenItem> getSkipTokens() {
        return skipTokens;
    }

    public List<String> getImports() {
        return imports;
    }

    public String getStartState() {
        return startState;
    }

    public String getName() {
        return name;
    }
}
