package de.ls5.wt2.jsonmanagedreference;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.List;

public class Group {

  public Long id;

  public String name;

  @JsonManagedReference
  // @JsonBackReference
  public List<User> users = new ArrayList<>();

  public Group() {
  }

  public Group(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}
