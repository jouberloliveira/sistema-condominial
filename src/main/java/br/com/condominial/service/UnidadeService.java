package br.com.condominial.service;

import br.com.condominial.domain.Unidade;
import br.com.condominial.repository.UnidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnidadeService {

    private final UnidadeRepository repository;

    public List<Unidade> listarTodas() {
        return repository.findAll();
    }

    public Unidade buscarPorId(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new BusinessException("Unidade não encontrada: " + id));
    }

    @Transactional
    public Unidade salvar(Unidade unidade) {
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
        buscarPorId(id);
        repository.deleteById(id);
    }
}
