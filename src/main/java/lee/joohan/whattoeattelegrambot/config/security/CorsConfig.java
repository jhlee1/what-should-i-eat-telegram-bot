package lee.joohan.whattoeattelegrambot.config.security;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * Created by Joohan Lee on 2020/06/06
 */

@EnableWebFlux
@Configuration
public class CorsConfig implements WebFluxConfigurer {
    @Value("#{'${webConfig.allowedOrigins}'.split(',')}")
    private List<String> origins;

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        String[] targetOrigins = origins.stream()
            .map(String::trim)
            .toArray(String[]::new);

        corsRegistry.addMapping("/**")
            .allowedOrigins(targetOrigins)
            .allowedHeaders("*")
            .allowedMethods("*")
            .maxAge(3600);
    }
}
