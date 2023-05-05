package de.ls5.wt2.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GroupDto {

  public Long id;

  public String name;

  public List<Long> userIds = new ArrayList<>();

  public static GroupDto of(Group group) {
    final GroupDto dto = new GroupDto();
    dto.id = group.id;
    dto.name = group.name;
    dto.userIds.addAll(group.users.stream()
        .map(u -> u.id)
        .collect(Collectors.toList())
    );
    return dto;
  }
}
