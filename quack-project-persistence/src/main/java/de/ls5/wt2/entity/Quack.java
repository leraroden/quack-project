package de.ls5.wt2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;



import java.util.Date;

@Entity
@Table(name="Quacks")
public class Quack extends DBIdentified{

    @Column(name = "author")
    private String author;

    @Column(name = "content")
    private String content;

    @Column(name = "publishedOn")
    private Date publishedOn;

    public Quack() {}

    public Quack(String username, String content){
        author = username;
        this.content = content;
        publishedOn = new Date();
    }

    public String getAuthor(){
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
