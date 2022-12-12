package com.mentor4you.domain.model;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {
  ADMIN("ADMIN"),
  MODERATOR("MODERATOR"),
  MENTOR("MENTOR"),
  MENTEE("MENTEE");

  private final String name;

  public static Role fromName(String name) {
    return Arrays.stream(Role.values())
        .filter(role -> role.getName().equals(name))
        .findFirst()
        .get();
  }
}
