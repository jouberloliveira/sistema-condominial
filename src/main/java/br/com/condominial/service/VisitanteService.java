package br.com.condominial.service;

import br.com.condominial.domain.Visitante;
import br.com.condominial.repository.VisitanteRepository;
import br.com.condominial.security.AccessControlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitanteService {

    private final VisitanteRepository repository;
    private final AccessControlService accessControl;

    public List<Visitante> listarTodos() {
        if (accessControl.isAdmin()) {
            return repository.findAll();
        }
        return repository.findByUnidadeId(accessControl.getUnidadeId());
    }

    public Visitante buscarPorId(Long id) {
        Visitante visitante = repository.findById(id)
            .orElseThrow(() -> new BusinessException("Visitante não encontrado: " + id));
        accessControl.verificarAcesso(visitante.getUnidade().getId());
        return visitante;
    }

    @Transactional
    public Visitante salvar(Visitante visitante) {
        accessControl.verificarAcesso(visitante.getUnidade().getId());
        if (visitante.getAutorizadoPor() != null) {
            boolean moradorDaMesmaUnidade = visitante.getAutorizadoPor().getUnidade().getId()
                .equals(visitante.getUnidade().getId());
            if (!moradorDaMesmaUnidade) {
                throw new BusinessException("O morador autorizador deve pertencer à mesma unidade do visitante");
            }
        }
        return repository.save(visitante);
    }

    @Transactional
    public void excluir(Long id) {
        Visitante visitante = buscarPorId(id); // verificarAcesso chamado dentro de buscarPorId
        repository.deleteById(visitante.getId());
    }
}
