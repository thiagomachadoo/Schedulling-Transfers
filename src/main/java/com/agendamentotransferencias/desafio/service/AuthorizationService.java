package com.agendamentotransferencias.desafio.service;

import com.agendamentotransferencias.desafio.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

@Service
@RequiredArgsConstructor
public class AuthorizationService implements UserDetailsService {

  private final UserRepository repository;

    @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return repository.findByLogin(username);
  }
}
