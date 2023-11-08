package io.incondensable.global.security.vo;

import io.incondensable.business.model.client.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author abbas
 */
public class CustomerDetails implements UserDetails {
    private final Long id;
    private final String email;
    private final String password;
    private final String name;
    private final List<GrantedAuthority> authorities;

    public CustomerDetails(Long id, String email, String password, String name, List<GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.authorities = authorities;
    }

    public static CustomerDetails mapEntityToUserDetails(Customer customer) {
        List<GrantedAuthority> authorities = new ArrayList<>(customer.getCustomerRoles().size());
        authorities.addAll(customer.getCustomerRoles());

        return new CustomerDetails(
                customer.getId(),
                customer.getEmail(),
                new SCryptPasswordEncoder().encode(customer.getPassword()),
                String.join(" ", customer.getFirstname(), customer.getLastname()),
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
