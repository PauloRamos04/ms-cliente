package saudeconectada.fatec.infra.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {
    private String username; // CPF
    private String firstName; // Nome
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    // Construtor
    public CustomUserDetails(String username, String firstName, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.firstName = firstName;
        this.password = password;
        this.authorities = authorities;
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
        return username;
    }

    public String getFirstName() {
        return firstName; // Retorna o firstName
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Lógica para a conta não expirada
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Lógica para a conta não bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Lógica para credenciais não expiradas
    }

    @Override
    public boolean isEnabled() {
        return true; // Lógica para a conta habilitada
    }
}
