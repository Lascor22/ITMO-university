package ru.itmo.wp.model.domain;

import java.io.Serializable;
import java.util.Date;

public abstract class AbstractModel implements Serializable {

    public abstract long getId();

    public abstract void setId(long id);

    public abstract Date getCreationTime();

    public abstract void setCreationTime(Date creationTime);
}
