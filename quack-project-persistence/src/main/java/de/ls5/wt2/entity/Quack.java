package de.ls5.wt2.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
public class Quack extends DBIdentified{

    @ManyToOne //Many Quacks are mapped to one User
    private User author;
    private String content;
    private Date publishedOn;

    public User getAuthor(){ return author; }
    public void setAuthor(User author){ this.author = author; }

    public String getContent(){ return content; }
    public void setContent(String content){ this.content = content; }

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public Date getPublishedOn(){ return publishedOn; }
    public void setPublishedOn(Date publishedOn){ this.publishedOn = publishedOn; }
}
