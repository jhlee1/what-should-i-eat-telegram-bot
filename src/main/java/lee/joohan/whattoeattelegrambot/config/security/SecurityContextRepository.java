package lee.joohan.whattoeattelegrambot.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
        ServerHttpRequest serverHttpRequest = exchange.getRequest();

        String authHeader = serverHttpRequest.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        String authToken = null;

        if (authHeader != null && authHeader.startsWith(TOKEN_PREFIX)) {
            authToken = authHeader.replace(TOKEN_PREFIX, "");
        } else {
            log.warn("Could not find bearer String, will ignore the header.");
        }

        if (authToken != null) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(authToken, authToken);

            return this.authenticationManager.authenticate(authentication).map(auth -> new SecurityContextImpl(auth));
        } else {
            return Mono.empty();
        }
    }
}
