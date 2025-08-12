package wu.huang.hflex.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository("apiKeyRepository")
public class ApiKeyRepository {
    private final Map<String, ApiKeyRecord> store = Map.of(
            "KEY_READ_123",  new ApiKeyRecord("client-read",  List.of(new SimpleGrantedAuthority("SCOPE_orders:read"))),
            "KEY_WRITE_456", new ApiKeyRecord("client-write", List.of(new SimpleGrantedAuthority("SCOPE_orders:write")))
    );
    public Optional<ApiKeyRecord> findByKey(String rawKey) { return Optional.ofNullable(store.get(rawKey)); }
    public record ApiKeyRecord(String clientId, List<GrantedAuthority> authorities) {}
}
