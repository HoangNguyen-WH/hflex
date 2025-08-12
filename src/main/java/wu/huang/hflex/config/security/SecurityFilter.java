package wu.huang.hflex.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityFilter {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, ApiKeyFilter apiKeyFilter, ApiKeyAuthProvider provider) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(provider)
                .addFilterBefore(apiKeyFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated()
                )
                .build();
    }
}
