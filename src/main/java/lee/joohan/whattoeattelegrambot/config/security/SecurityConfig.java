package lee.joohan.whattoeattelegrambot.config.security;

import lee.joohan.whattoeattelegrambot.config.oauth.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final AuthenticationManager authenticationManager;
  private final SecurityContextRepository securityContextRepository;
  private final CustomOAuth2UserService customOAuth2UserService;

  @Bean
  SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity httpSecurity) {
    String[] exceptions = new String[] {"/auth/**"};

//    erverHttpSecurity
//                .csrf().disable()
//                .httpBasic().disable()
//                .formLogin().disable()
//                .authorizeExchange()
//                .pathMatchers("/api/**").authenticated()
//                .anyExchange().permitAll()
//                .and().oauth2Login(withDefaults())
//                .logout()
//                .and().exceptionHandling()
//                .accessDeniedHandler((exchange, exception) -> Mono.error(new ApplicationException(ApplicationType.ACCESS_DENIED)))
//                .and().build()
//                ;

    return httpSecurity
        .cors().disable()
        .httpBasic().disable()
        .formLogin().disable()
        .oauth2Login()
        .authorizedClientService()
        .logout()
        .and()
        .exceptionHandling()
        .authenticationEntryPoint((exchange, e) -> Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
        .accessDeniedHandler((exchange, denied) -> Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN)))
        .and()
        .csrf().disable()
        .authenticationManager(authenticationManager)
        .securityContextRepository(securityContextRepository)
        .authorizeExchange()
        .pathMatchers(exceptions).permitAll()
        .pathMatchers(HttpMethod.OPTIONS).permitAll()
        .anyExchange().authenticated()
        .and()
        .build();
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    String[] exceptions = new String[] {"/auth/**", "/css/**", "/images/**", "/js/**"};

    httpSecurity.csrf().disable()
        .authorizeRequests()
        .antMatchers(exceptions).permitAll()
        .antMatchers(HttpMethod.OPTIONS).permitAll()
        .anyRequest().authenticated()
        .and()
        .logout().logoutSuccessUrl("/")
        .and()
        .oauth2Login()
        .userInfoEndpoint()
        .userService(customOAuth2UserService);

  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() { //TODO: 필요 없어보이는데 확인하고 빼기
    return new BCryptPasswordEncoder();
  }
}
