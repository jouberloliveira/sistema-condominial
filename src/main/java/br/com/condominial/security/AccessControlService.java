package br.com.condominial.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AccessControlService {

    public boolean isAdmin() {
        return getAuthentication().getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    public Long getUnidadeId() {
        CondominioUserDetails user = getPrincipal();
        if (user.getUnidadeId() == null) {
            throw new IllegalStateException("Usuário sem unidade associada");
        }
        return user.getUnidadeId();
    }

    public void verificarAcesso(Long unidadeId) {
        if (isAdmin()) return;
        if (unidadeId == null || !unidadeId.equals(getUnidadeId())) {
            throw new AccessDeniedException("Acesso negado: você não tem permissão para acessar esta entidade");
        }
    }

    private CondominioUserDetails getPrincipal() {
        Authentication auth = getAuthentication();
        if (auth.getPrincipal() instanceof CondominioUserDetails u) return u;
        throw new IllegalStateException("Principal inesperado: " + auth.getPrincipal().getClass());
    }

    private Authentication getAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new IllegalStateException("Usuário não autenticado");
        }
        return auth;
    }
}
