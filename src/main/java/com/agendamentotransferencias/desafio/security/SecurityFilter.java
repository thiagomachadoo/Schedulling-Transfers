package com.agendamentotransferencias.desafio.security;

import com.agendamentotransferencias.desafio.repository.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.web.context.*;
import org.springframework.stereotype.*;
import org.springframework.web.filter.*;

import java.io.*;
@RequiredArgsConstructor
@Component
public class SecurityFilter extends OncePerRequestFilter {

  private final UserRepository userRepository;
  private final TokenService tokenService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    var token = this.recoverToken(request);

    if (token != null){
      var login = tokenService.validateToken(token);
      UserDetails user = userRepository.findByLogin(login);

      var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(auth);
    }
    filterChain.doFilter(request, response);
  }

  private String recoverToken(HttpServletRequest request) {
    var authHeader = request.getHeader("Authorization");
    if (authHeader == null) return null;
    return authHeader.replace("Bearer ", "");
  }
}
