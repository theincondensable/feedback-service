package io.incondensable.global.security.vo;

import io.incondensable.business.model.client.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author abbas
 */
public class FeedbackUserDetails implements UserDetails {
    private final Long id;
    private final String email;
    private final String password;
    private final String name;
    private final List<GrantedAuthority> authorities;

    public FeedbackUserDetails(Long id, String email, String password, String name, List<GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.authorities = authorities;
    }

    public static FeedbackUserDetails mapEntityToUserDetails(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>(user.getRoles().size());
        authorities.addAll(user.getRoles());

        return new FeedbackUserDetails(
                user.getId(),
                user.getEmail(),
                new SCryptPasswordEncoder().encode(user.getPassword()),
                String.join(" ", user.getFirstname(), user.getLastname()),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
