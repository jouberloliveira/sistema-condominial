package br.com.condominial.service;

import br.com.condominial.domain.Unidade;
import br.com.condominial.repository.UnidadeRepository;
import br.com.condominial.security.AccessControlService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnidadeService {

    private final UnidadeRepository repository;
    private final AccessControlService accessControl;

    public List<Unidade> listarTodas() {
        if (accessControl.isAdmin()) {
            return repository.findAll();
        }
        // Usuário comum vê apenas sua própria unidade (para uso em dropdowns)
        Long unidadeId = accessControl.getUnidadeId();
        return repository.findById(unidadeId)
            .map(List::of)
            .orElse(List.of());
    }

    public Unidade buscarPorId(Long id) {
        Unidade unidade = repository.findById(id)
            .orElseThrow(() -> new BusinessException("Unidade não encontrada: " + id));
        accessControl.verificarAcesso(unidade.getId());
        return unidade;
    }

    @Transactional
    public Unidade salvar(Unidade unidade) {
        if (!accessControl.isAdmin()) {
            throw new AccessDeniedException("Apenas administradores podem criar ou editar unidades");
        }
        boolean duplicata = unidade.getId() == null
            ? repository.findByBlocoAndNumero(unidade.getBloco(), unidade.getNumero()).isPresent()
            : repository.existsByBlocoAndNumeroAndIdNot(unidade.getBloco(), unidade.getNumero(), unidade.getId());
        if (duplicata) {
            throw new BusinessException("Já existe unidade com bloco " + unidade.getBloco() + " e número " + unidade.getNumero());
        }
        return repository.save(unidade);
    }

    @Transactional
    public void excluir(Long id) {
        if (!accessControl.isAdmin()) {
            throw new AccessDeniedException("Apenas administradores podem excluir unidades");
        }
        buscarPorId(id);
        repository.deleteById(id);
    }
}
