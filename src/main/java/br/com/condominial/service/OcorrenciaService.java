package br.com.condominial.service;

import br.com.condominial.domain.Ocorrencia;
import br.com.condominial.enums.StatusOcorrencia;
import br.com.condominial.repository.OcorrenciaRepository;
import br.com.condominial.security.AccessControlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OcorrenciaService {

    private final OcorrenciaRepository repository;
    private final AccessControlService accessControl;

    public List<Ocorrencia> listarTodas() {
        if (accessControl.isAdmin()) {
            return repository.findAll();
        }
        return repository.findByUnidadeId(accessControl.getUnidadeId());
    }

    public Ocorrencia buscarPorId(Long id) {
        Ocorrencia ocorrencia = repository.findById(id)
            .orElseThrow(() -> new BusinessException("Ocorrência não encontrada: " + id));
        if (ocorrencia.getUnidade() != null) {
            accessControl.verificarAcesso(ocorrencia.getUnidade().getId());
        }
        return ocorrencia;
    }

    @Transactional
    public Ocorrencia salvar(Ocorrencia ocorrencia) {
        if (ocorrencia.getUnidade() != null) {
            accessControl.verificarAcesso(ocorrencia.getUnidade().getId());
        }
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
        Ocorrencia ocorrencia = buscarPorId(id); // verificarAcesso chamado dentro de buscarPorId
        repository.deleteById(ocorrencia.getId());
    }
}
