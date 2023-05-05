package de.ls5.wt2.jsonidentitiyinfo;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id")
public class Group {

  public Long id;

  public String name;

  public List<User> users = new ArrayList<>();

  public Group() {
  }

  public Group(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}
