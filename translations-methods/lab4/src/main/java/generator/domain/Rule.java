package generator.domain;

import java.util.ArrayList;
import java.util.List;

public class Rule {
    private final List<String> items = new ArrayList<>();
    private final List<String> parameters = new ArrayList<>();
    private final List<String> actions = new ArrayList<>();

    public void addItem(String s, String parameter, String action) {
        items.add(s);
        parameters.add(parameter);
        actions.add(action);
    }

    public List<String> getItems() {
        return items;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public List<String> getActions() {
        return actions;
    }
}
