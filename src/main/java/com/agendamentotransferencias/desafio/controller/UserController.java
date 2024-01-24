package com.agendamentotransferencias.desafio.controller;

import com.agendamentotransferencias.desafio.model.User;
import com.agendamentotransferencias.desafio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "user-actions")
public class UserController {

  private final UserRepository userRepository;


  @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> createNewUser(@RequestBody User newUser) {

    User user = userRepository.save(newUser);

    return new ResponseEntity<>("Usuario criado com sucesso!\n" + user, HttpStatus.CREATED);
  }

  @GetMapping(path = "/getAll-users")
  public List<User> findAllUsers() {

    return userRepository.findAll();
  }
}

