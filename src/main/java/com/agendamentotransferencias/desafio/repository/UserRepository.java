package com.agendamentotransferencias.desafio.repository;

import com.agendamentotransferencias.desafio.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.*;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, String> {

   Optional<User> findByLoginIgnoreCase(String username);

   UserDetails findByLogin(String login);

}
