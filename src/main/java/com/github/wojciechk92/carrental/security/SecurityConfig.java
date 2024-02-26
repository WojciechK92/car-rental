package com.github.wojciechk92.carrental.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtDecoder jwtDecoder) throws Exception {
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeyCloakRoleConverter());

    http.authorizeHttpRequests(auth -> auth
            .requestMatchers("/clients/**").authenticated()
            .requestMatchers("/employees/**").authenticated()
            .requestMatchers("/rentals/**").authenticated()
            .anyRequest().permitAll())
            .csrf(AbstractHttpConfigurer::disable);
    http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt
            .decoder(jwtDecoder)
            .jwtAuthenticationConverter(jwtAuthenticationConverter)));
    return http.build();
  }

  @Bean
  public JwtDecoder jwtDecoder() {
    String issueUri = "http://localhost:9000/realms/CarRental";
    NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder) JwtDecoders.fromIssuerLocation(issueUri);
    return jwtDecoder;
  }

  public class KeyCloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
      Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");
      if (realmAccess == null || realmAccess.isEmpty()) {
        return new ArrayList<>();
      }

      Collection<GrantedAuthority> roles = ((List<String>) realmAccess.get("roles")).stream()
              .map(roleName -> "ROLE_" + roleName)
              .map(SimpleGrantedAuthority::new)
              .collect(Collectors.toList());

      return roles;
    }
  }
}
