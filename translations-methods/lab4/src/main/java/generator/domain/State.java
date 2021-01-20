package generator.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class State {
    private String name;
    private final List<String> parameters = new ArrayList<>();
    private final List<String> returns = new ArrayList<>();
    private final Set<String> first = new HashSet<>();
    private final Set<String> follow = new HashSet<>();
    private final List<Rule> rules = new ArrayList<>();
    private boolean hasEpsilon = false;

    public void addParameter(String type, String name) {
        String res = type + " " + name;
        parameters.add(res);
    }

    public void addReturn(String type, String name) {
        String res = type + " " + name;
        returns.add(res);
    }

    public void addRule(Rule r) {
        rules.add(r);
        if (r.getItems().size() == 1 && r.getItems().get(0).equals("EPS")) {
            hasEpsilon = true;
        }
    }

    public boolean addToFirst(String item) {
        if (item.equals("EPS")) {
            hasEpsilon = true;
        }
        if (first.contains(item)) {
            return false;
        }
        return first.add(item);
    }

    public boolean noEpsilon() {
        return !hasEpsilon;
    }

    public boolean addToFollow(String item) {
        if (follow.contains(item)) {
            return false;
        }
        return follow.add(item);
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public String getName() {
        return name;
    }

    public Set<String> getFirst() {
        return first;
    }

    public Set<String> getFollow() {
        return follow;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public List<String> getReturns() {
        return returns;
    }
}
