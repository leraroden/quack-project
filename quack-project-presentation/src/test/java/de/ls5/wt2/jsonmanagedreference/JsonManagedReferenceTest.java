package de.ls5.wt2.jsonmanagedreference;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

public class JsonManagedReferenceTest {

  /**
   * group -> user1, user2
   */
  @Test
  public void serialize() throws Exception {
    final Group group = new Group(1L, "g1");

    final User user1 = new User(3L, "user1@test.de");
    final User user2 = new User(4L, "user2@test.de");

    user1.group = group;
    user2.group = group;
    group.users.add(user1);
    group.users.add(user2);

    final ObjectMapper om = new ObjectMapper();
    final String serializedGroup = om.writerWithDefaultPrettyPrinter().writeValueAsString(group);
    final String serializedUser = om.writerWithDefaultPrettyPrinter().writeValueAsString(user1);

    System.out.println("\ngroup:\n");
    System.out.println(serializedGroup);
    System.out.println("\nuser:\n");
    System.out.println(serializedUser);
  }
}
