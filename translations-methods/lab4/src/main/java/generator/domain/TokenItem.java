package generator.domain;

public class TokenItem {
    private final String name;
    private final String pattern;

    public TokenItem(String name, String pattern) {
        this.name = name;
        this.pattern = pattern;
    }

    public String getName() {
        return name;
    }

    public String getPattern() {
        return pattern;
    }
}
