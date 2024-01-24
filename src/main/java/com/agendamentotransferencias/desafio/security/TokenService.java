package com.agendamentotransferencias.desafio.security;

import com.agendamentotransferencias.desafio.model.*;
import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.*;
import com.auth0.jwt.exceptions.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.time.*;

@Service
public class TokenService {
  @Value("${api.security.token.secret}")
  private String secret;

  public String generateToken(User user){
    try{
      Algorithm algorithm = Algorithm.HMAC256(secret);
      return JWT.create()
          .withIssuer("desafio-api")
          .withSubject(user.getLogin())
          .withExpiresAt(generateExpirationDate())
          .sign(algorithm);

    } catch (JWTCreationException e){
      throw new RuntimeException("Error generate token", e);
    }
  }

  public String validateToken(String token){
    try{
      Algorithm algorithm = Algorithm.HMAC256(secret);
      return JWT.require(algorithm)
          .withIssuer("desafio-api")
          .build()
          .verify(token)
          .getSubject();

    } catch (JWTCreationException e){
     return "";
    }
  }

  private Instant generateExpirationDate(){
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
  }
}
