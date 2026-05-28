package br.com.condominial.service;

import br.com.condominial.domain.Morador;
import br.com.condominial.domain.Unidade;
import br.com.condominial.enums.SimNao;
import br.com.condominial.repository.MoradorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MoradorService {

    private final MoradorRepository repository;

    public List<Morador> listarTodos() {
        return repository.findAll();
    }

    public Morador buscarPorId(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new BusinessException("Morador não encontrado: " + id));
    }

    public List<Morador> listarPorUnidade(Unidade unidade) {
        return repository.findByUnidade(unidade);
    }

    @Transactional
    public Morador salvar(Morador morador) {
        boolean cpfDuplicado = morador.getId() == null
            ? repository.findByCpf(morador.getCpf()).isPresent()
            : repository.existsByCpfAndIdNot(morador.getCpf(), morador.getId());
        if (cpfDuplicado) {
            log.warn("Tentativa de cadastro com CPF duplicado: {}", maskCpf(morador.getCpf()));
            throw new BusinessException("CPF já cadastrado");
        }
        if (morador.getResponsavel() == SimNao.SIM) {
            long count = morador.getId() == null
                ? repository.countByUnidadeAndResponsavel(morador.getUnidade(), SimNao.SIM)
                : repository.countByUnidadeAndResponsavelAndIdNot(morador.getUnidade(), SimNao.SIM, morador.getId());
            if (count >= 1) {
                throw new BusinessException("Já existe um responsável cadastrado para esta unidade");
            }
        }
        return repository.save(morador);
    }

    @Transactional
    public void excluir(Long id) {
        buscarPorId(id);
        repository.deleteById(id);
    }

    private static String maskCpf(String cpf) {
        if (cpf == null) return "***";
        String digits = cpf.replaceAll("[^\\d]", "");
        if (digits.length() < 9) return "***";
        return "***.***.%s-**".formatted(digits.substring(6, 9));
    }
}
