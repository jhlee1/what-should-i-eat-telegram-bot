package lee.joohan.whattoeattelegrambot.config.security;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/05/18
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class SecurityContextRepository implements ServerSecurityContextRepository {
    private final AuthenticationManager authenticationManager;

    private static final String TOKEN_PREFIX = "Bearer ";

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        return Optional.ofNullable(exchange.getRequest().getHeaders())
            .map(headers -> headers.getFirst(HttpHeaders.AUTHORIZATION))
            .filter(authHeader -> authHeader.startsWith(TOKEN_PREFIX))
            .map(authHeader -> authHeader.replace(TOKEN_PREFIX, ""))
            .map(authToken -> new UsernamePasswordAuthenticationToken(authToken, authToken))
            .map(authentication -> this.authenticationManager.authenticate(authentication).map(auth -> (SecurityContext) new SecurityContextImpl(auth)))
            .orElseGet(() -> {
                log.warn("Could not find bearer String, will ignore the header.");

                return Mono.empty();
            });
    }
}
