package br.com.condominial.security;

import br.com.condominial.domain.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CondominioUserDetails implements UserDetails {

    private final Long usuarioId;
    private final String username;
    private final String password;
    private final Long unidadeId;
    private final Collection<? extends GrantedAuthority> authorities;

    public CondominioUserDetails(Usuario usuario) {
        this.usuarioId = usuario.getId();
        this.username = usuario.getUsername();
        this.password = usuario.getPassword();
        this.unidadeId = usuario.getUnidade() != null ? usuario.getUnidade().getId() : null;
        this.authorities = List.of(new SimpleGrantedAuthority(usuario.getRole()));
    }

    public Long getUsuarioId() { return usuarioId; }
    public Long getUnidadeId() { return unidadeId; }

    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }
    @Override public String getPassword() { return password; }
    @Override public String getUsername() { return username; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
