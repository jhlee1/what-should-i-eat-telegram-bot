package lee.joohan.whattoeattelegrambot.config;

import lee.joohan.whattoeattelegrambot.common.AuthenticationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/05/18
 */

@RequiredArgsConstructor
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
  private final AuthenticationManager authenticationManager;
  private final SecurityContextRepository securityContextRepository;

  @Bean
  SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity httpSecurity) {
    String[] exceptions = new String[] {"/auth/**"};

    return httpSecurity.cors().disable()
        .exceptionHandling()
        .authenticationEntryPoint((exchange, e) -> Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
        .accessDeniedHandler((exchange, denied) -> Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN)))
        .and()
        .csrf()
        .disable()
        .authenticationManager(authenticationManager)
  }


//    @Bean
//    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
//        String[] patterns = new String[] {"/auth/**"};
//        return http.cors().disable()
//                .exceptionHandling()
//                .authenticationEntryPoint((swe, e) -> Mono.fromRunnable(() -> {
//                    swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//                })).accessDeniedHandler((swe, e) -> Mono.fromRunnable(() -> {
//                    swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
//                })).and()
//                .csrf().disable()
//                .authenticationManager(authenticationManager)
//                .securityContextRepository(securityContextRepository)
//                .authorizeExchange()
//                .pathMatchers(patterns).permitAll()
//                .pathMatchers(HttpMethod.OPTIONS).permitAll()
//                .anyExchange().authenticated()
//                .and()
//                .build();
//    }
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//
//}
}
