package de.ls5.wt2.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.util.Date;

@Entity
@Table(name="Quacks")
public class Quack extends DBIdentified{

    @ManyToOne
    private User author;

    private String authorName;

    @Column(name = "content")
    private String content;

    @Column(name = "publishedOn")
    private Date publishedOn;

    @JsonIgnore
    public User getAuthor(){
        return author;
    }
    public String getAuthorName(){return authorName;}
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

    public void setAuthor(User author){this.author = author;}
    public void setAuthorName(String authorName){this.authorName = authorName;}

    public void setPublishedOn(Date date){this.publishedOn = date;}
}
