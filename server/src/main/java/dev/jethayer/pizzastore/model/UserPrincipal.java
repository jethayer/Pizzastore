package dev.jethayer.pizzastore.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Represents the authenticated user's details and their authorities.
 */
public class UserPrincipal implements UserDetails {

    private Users user;

    /**
     * Constructs a UserPrincipal with the given user.
     *
     * @param user the user associated with this UserPrincipal
     */
    public UserPrincipal(Users user) {
        this.user = user;
    }

    /**
     * Retrieves the role of the user.
     *
     * @return the user's role
     */
    public String getRole() {
        return user.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(getRole()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
