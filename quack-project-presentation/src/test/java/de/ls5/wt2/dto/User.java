package de.ls5.wt2.dto;

import java.util.ArrayList;
import java.util.List;

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
