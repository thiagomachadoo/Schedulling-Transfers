package com.agendamentotransferencias.desafio.controller;


import com.agendamentotransferencias.desafio.model.*;
import com.agendamentotransferencias.desafio.repository.*;
import com.agendamentotransferencias.desafio.security.*;
import com.agendamentotransferencias.desafio.service.UserService;
import jakarta.validation.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthenticationController {


  private final AuthenticationManager authenticationManager;
  private final UserRepository repository;
  private final TokenService tokenService;
  private final UserService userService;

  @PostMapping("/login")
  public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
    var usernamePassword = new UsernamePasswordAuthenticationToken(data.getLogin(), data.getPassword());
    var auth = this.authenticationManager.authenticate(usernamePassword);

    var token = tokenService.generateToken((User) auth.getPrincipal());

    return ResponseEntity.ok(new LoginResponseDTO(token));
  }

  @PostMapping("/register")
  public ResponseEntity register(@RequestBody @Valid AuthenticationDTO data){
    if (this.repository.findByLogin(data.getLogin()) != null) return ResponseEntity.badRequest().build();

    var encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
    var newUser = new User(data.getLogin(), encryptedPassword);
    var user = userService.isValidUser(newUser);

    if (user.isPresent()){
      repository.save(newUser);
      return new ResponseEntity("Usuario criado com sucesso!", HttpStatus.CREATED);
    }

    return new ResponseEntity("Usuário não possui dados completos para cadastro! Tente novamente", HttpStatus.BAD_REQUEST);
  }
}
