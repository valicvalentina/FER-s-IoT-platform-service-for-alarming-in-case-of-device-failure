package com.alarms.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
public class WebSecurityConfiguration {

  @Bean
  protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
    return new NullAuthenticatedSessionStrategy();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    return http.csrf()
      .disable()
      .cors()
      .and()
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/a").permitAll() // omogućuje pristup svima
        .anyRequest().authenticated())
      .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(
          jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
      .build();
  }

  // postavljanje JwtDekodera
  @Bean
  JwtDecoder jwtDecoder(@Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}") String uri) {
      return NimbusJwtDecoder.withJwkSetUri(uri + "/protocol/openid-connect/certs").build();
  }


  // definiranje convertera koji koristi preferred_username za ime
  // i postavlja role -> grantedAuthority converter
  public Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
    JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
    jwtConverter.setPrincipalClaimName("preferred_username");
    jwtConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRealmRoleConverter());
    return jwtConverter;
  }

  // converter uloga u granted authority
  public static class KeycloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
      final Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");
      return ((List<String>) realmAccess.get("roles")).stream()
        .map(roleName -> "ROLE_" + roleName) // prefix to map to a Spring Security "role"
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
    }
  }
}




