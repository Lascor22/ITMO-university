package ru.itmo.wp.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class DisableAccountCredentials {
    private long userId;
    private boolean change;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public boolean getChange() {
        return change;
    }

    public void setChange(boolean change) {
        this.change = change;
    }
}
