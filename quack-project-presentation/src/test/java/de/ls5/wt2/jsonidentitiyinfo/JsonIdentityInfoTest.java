package de.ls5.wt2.jsonidentitiyinfo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

public class JsonIdentityInfoTest {

  /**
   * group1 -> user1, user2
   * group2 -> user2, user3
   */
  @Test
  public void serialize() throws Exception {
    final Group group1 = new Group(1L, "g1");
    final Group group2 = new Group(2L, "g2");

    final User user1 = new User(3L, "user1@test.de");
    final User user2 = new User(4L, "user2@test.de");
    final User user3 = new User(5L, "user3@test.de");

    group1.users.add(user1);
    group1.users.add(user2);
    user1.groups.add(group1);
    user2.groups.add(group1);

    group2.users.add(user2);
    group2.users.add(user3);
    user2.groups.add(group2);
    user3.groups.add(group2);

    final ObjectMapper om = new ObjectMapper();
    final String user1json = om.writerWithDefaultPrettyPrinter().writeValueAsString(user1);
    final String group1json = om.writerWithDefaultPrettyPrinter().writeValueAsString(user1);

    System.out.println("\nuser1:\n");
    System.out.println(user1json);
    System.out.println("\ngroup1:\n");
    System.out.println(group1json);
  }
}
