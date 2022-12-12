package com.mentor4you.domain.DTO.jwt;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtTokenResponse {

  private String accessToken;
}
