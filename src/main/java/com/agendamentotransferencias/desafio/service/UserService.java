package com.agendamentotransferencias.desafio.service;

import com.agendamentotransferencias.desafio.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    public Optional<User> isValidUser(User user){

        if (user.getLogin() != null && user.getPassword() != null) return Optional.of(user);
        return Optional.empty();
    }
}
