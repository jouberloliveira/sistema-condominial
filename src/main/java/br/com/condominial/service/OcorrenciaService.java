package br.com.condominial.service;

import br.com.condominial.domain.Ocorrencia;
import br.com.condominial.enums.StatusOcorrencia;
import br.com.condominial.repository.OcorrenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OcorrenciaService {

    private final OcorrenciaRepository repository;

    public List<Ocorrencia> listarTodas() {
        return repository.findAll();
    }

    public Ocorrencia buscarPorId(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new BusinessException("Ocorrência não encontrada: " + id));
    }

    @Transactional
    public Ocorrencia salvar(Ocorrencia ocorrencia) {
        if (ocorrencia.getStatus() == StatusOcorrencia.RESOLVIDA || ocorrencia.getStatus() == StatusOcorrencia.CANCELADA) {
            if (ocorrencia.getDataFechamento() == null) {
                throw new BusinessException("Data de fechamento é obrigatória quando status é RESOLVIDA ou CANCELADA");
            }
            if (ocorrencia.getDataFechamento().isBefore(ocorrencia.getDataAbertura())) {
                throw new BusinessException("Data de fechamento não pode ser anterior à data de abertura");
            }
        }
        return repository.save(ocorrencia);
    }

    @Transactional
    public void excluir(Long id) {
        buscarPorId(id);
        repository.deleteById(id);
    }
}
