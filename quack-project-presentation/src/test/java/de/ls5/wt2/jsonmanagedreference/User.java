package de.ls5.wt2.jsonmanagedreference;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class User {

  public Long id;

  public String email;

  @JsonBackReference
  // @JsonManagedReference
  public Group group;

  public User() {
  }

  public User(Long id, String email) {
    this.id = id;
    this.email = email;
  }
}
