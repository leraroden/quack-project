package de.ls5.wt2.entity;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;



import java.util.Date;

@Entity
@Table(name="Quacks")
public class Quack extends DBIdentified{

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Column(name = "content")
    private String content;

    @Column(name = "publishedOn")
    private Date publishedOn;

    public Quack() {}

    public Quack(User author, String content){
        this.author = author;
        this.content = content;
        publishedOn = new Date();
    }

    public User getAuthor(){
        return author;
    }
    public String getContent(){
        return content;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso = ISO.DATE_TIME)
    public Date getPublishedOn(){
        return publishedOn;
    }

    public void setContent(String content){
        this.content = content;
    }
}
