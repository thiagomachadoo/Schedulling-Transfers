package com.agendamentotransferencias.desafio.repository;

import com.agendamentotransferencias.desafio.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.*;

public interface UserRepository extends JpaRepository<User, String> {
   UserDetails findByLogin(String login);
}
