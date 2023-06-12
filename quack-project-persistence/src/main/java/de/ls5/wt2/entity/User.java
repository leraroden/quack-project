package de.ls5.wt2.entity;

import com.fasterxml.jackson.annotation.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Users")
public class User extends DBIdentified  {

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @JsonIgnore
    @OneToMany
    private List<Quack> quackList = new ArrayList<>();

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public List<Quack> getQuackList(){
        return quackList;
    }
    public void addQuack(Quack newQuack){
        quackList.add(newQuack);
    }
    public void deleteQuack(Quack quack){
        quackList.remove(quack);
    }
}
