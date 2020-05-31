package lee.joohan.whattoeattelegrambot.config.security;

import org.bson.types.ObjectId;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

public class AuthenticatedUser implements Authentication {
    private String email;
    private boolean authenticated;
    private List<SimpleGrantedAuthority> roles;
    private Object userId;

    public AuthenticatedUser(String email, List<SimpleGrantedAuthority> roles, Object userId){
        this.email = email;
        this.roles = roles;
        this.authenticated = true;
        this.userId = userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public Object getCredentials() {
        return userId;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return userId;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return email;
    }
}
