package br.com.condominial.service;

import br.com.condominial.domain.Visitante;
import br.com.condominial.repository.VisitanteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitanteService {

    private final VisitanteRepository repository;

    public List<Visitante> listarTodos() {
        return repository.findAll();
    }

    public Visitante buscarPorId(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new BusinessException("Visitante não encontrado: " + id));
    }

    @Transactional
    public Visitante salvar(Visitante visitante) {
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
        buscarPorId(id);
        repository.deleteById(id);
    }
}
