package ru.itmo.wp.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PostForm {
    @NotNull
    @NotEmpty
    @Size(min = 5, max = 60)
    private String title;

    @Pattern(regexp = "[a-z ]*", message = "тэг должен состоять из латинских символов")
    private String StringOfTags;

    @NotNull
    @NotEmpty
    @Size(min = 5, max = 60000)
    private String text;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStringOfTags() {
        return StringOfTags;
    }

    public void setStringOfTags(String stringOfTags) {
        StringOfTags = stringOfTags;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
