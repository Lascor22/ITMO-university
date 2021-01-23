package ru.itmo.wp.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(
        indexes = @Index(columnList = "creationTime")
)
public class Notice {

    @Id
    @GeneratedValue
    private long id;

    @Lob
    @NotEmpty
    private String content;

    @CreationTimestamp
    private Date creationTime;

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
