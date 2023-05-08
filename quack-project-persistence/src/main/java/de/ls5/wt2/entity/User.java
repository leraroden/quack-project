package de.ls5.wt2.entity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class User extends DBIdentified{

    private String email;
    private String password;
    private String nickname;
    @OneToMany  // One User is mapped to many Quacks
    private Set<Quack> quacks;

    public String getEmail() { return email;}
    public void setEmail(String email){ this.email = email; }

    public String getPassword(){ return password; }
    public void setPassword(String password){ this.password = password; }

    public String getNickname(){ return nickname; }
    public void setNickname(String nickname){ this.nickname = nickname; }

    public Set<Quack> getQuacks(){ return quacks; }
    public void setQuack(Set<Quack> quacks){ this.quacks = quacks; }
}
