package userManagement.example.UserManagementSystem.Security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import userManagement.example.UserManagementSystem.entity.User;

import java.util.Collection;

public class JWTAuthentication implements Authentication {
    String jwt;
    User user;
    public JWTAuthentication(String jwt){
        this.jwt = jwt;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }


    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public User getPrincipal() {
        return user;
    }

    @Override
    public boolean isAuthenticated() {
        return (user != null);
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return null;
    }
}
