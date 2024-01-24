package com.agendamentotransferencias.desafio.model;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationDTO {

  private String login;
  private String password;
}
