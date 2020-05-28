package lee.joohan.whattoeattelegrambot.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/05/18
 */

@RequiredArgsConstructor
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {
  private final AuthenticationManager authenticationManager;
  private final SecurityContextRepository securityContextRepository;

  @Bean
  SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity httpSecurity) {
    String[] exceptions = new String[] {"/auth/**", "/login/oauth2/code/google"};

    return httpSecurity
        .cors().disable()
        .csrf().disable()
        .httpBasic().disable()
        .authenticationManager(authenticationManager)
        .securityContextRepository(securityContextRepository)
        .exceptionHandling()
        .authenticationEntryPoint((exchange, e) -> Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
        .accessDeniedHandler((exchange, denied) -> Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN)))
        .and()
        .authorizeExchange()
        .pathMatchers(exceptions).permitAll()
        .pathMatchers(HttpMethod.OPTIONS).permitAll()
        .anyExchange().authenticated()
        .and()
        .build();
  }



  @Bean
  public BCryptPasswordEncoder passwordEncoder() { //TODO: 필요 없어보이는데 확인하고 빼기
    return new BCryptPasswordEncoder();
  }
}
