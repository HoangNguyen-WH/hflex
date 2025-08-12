package wu.huang.hflex.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class ApiKeyAuthProvider implements AuthenticationProvider {
    private final ApiKeyRepository apiKeyRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String apiKey = authentication.getCredentials().toString();
        return apiKeyRepository.findByKey(apiKey)
                .map(apiKeyRecord -> new ApiKey(apiKeyRecord.authorities(), apiKey, apiKeyRecord.clientId()))
                .orElseThrow(() -> new BadCredentialsException("Invalid API key"));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ApiKey.class.isAssignableFrom(authentication);
    }

    public static class ApiKey extends AbstractAuthenticationToken {

        private final String apiKey;
        private final String principal;

        public ApiKey(Collection<? extends GrantedAuthority> authorities, String apiKey, String principal) {
            super(authorities);
            this.apiKey = apiKey;
            this.principal = principal;
            setAuthenticated(true);
        }

        public ApiKey(String apiKey) {
            super(null);
            this.apiKey = apiKey;
            this.principal = null;
            setAuthenticated(false);
        }

        @Override
        public Object getCredentials() {
            return apiKey;
        }

        @Override
        public Object getPrincipal() {
            return principal;
        }
    }
}
