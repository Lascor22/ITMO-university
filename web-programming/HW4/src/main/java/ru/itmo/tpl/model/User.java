package ru.itmo.tpl.model;

public class User {
    private final long id;
    private final String handle;
    private final String name;
    private final Color color;
    private long countPosts;

    public User(long id, String handle, String name, Color color, long countPosts) {
        this.id = id;
        this.handle = handle;
        this.name = name;
        this.color = color;
        this.countPosts = countPosts;
    }

    public long getCountPosts() {
        return countPosts;
    }

    public long getId() {
        return id;
    }

    public String getHandle() {
        return handle;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
}
