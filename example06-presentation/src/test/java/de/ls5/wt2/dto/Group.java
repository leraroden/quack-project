package de.ls5.wt2.dto;

import java.util.ArrayList;
import java.util.List;

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
