package de.ls5.wt2.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserDto {

  public Long id;

  public String email;

  public List<Long> groupIds = new ArrayList<>();

  public static UserDto of(User user) {
    final UserDto dto = new UserDto();
    dto.id = user.id;
    dto.email = user.email;
    dto.groupIds.addAll(user.groups.stream()
        .map(g -> g.id)
        .collect(Collectors.toList())
    );
    return dto;
  }
}
