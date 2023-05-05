package de.ls5.wt2.jsonidentitiyinfo;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id")
public class User {

  public Long id;

  public String email;

  public List<Group> groups = new ArrayList<>();

  public User() {
  }

  public User(Long id, String email) {
    this.id = id;
    this.email = email;
  }
}
