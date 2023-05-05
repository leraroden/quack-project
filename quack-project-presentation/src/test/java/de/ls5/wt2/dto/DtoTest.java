package de.ls5.wt2.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

public class DtoTest {

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

    final UserDto user1Dto = UserDto.of(user1);
    final GroupDto group1Dto = GroupDto.of(group1);

    final ObjectMapper om = new ObjectMapper();
    final String user1Json = om.writerWithDefaultPrettyPrinter().writeValueAsString(user1Dto);
    final String group1Json = om.writerWithDefaultPrettyPrinter().writeValueAsString(group1Dto);

    System.out.println("\nuser1:\n");
    System.out.println(user1Json);
    System.out.println("\ngroup1:\n");
    System.out.println(group1Json);
  }
}
