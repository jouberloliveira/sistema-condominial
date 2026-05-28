package br.com.condominial.repository;

import br.com.condominial.domain.Morador;
import br.com.condominial.domain.Unidade;
import br.com.condominial.enums.SimNao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MoradorRepository extends JpaRepository<Morador, Long> {
    Optional<Morador> findByCpf(String cpf);
    boolean existsByCpfAndIdNot(String cpf, Long id);
    List<Morador> findByUnidade(Unidade unidade);
    List<Morador> findByUnidadeId(Long unidadeId);
    long countByUnidadeAndResponsavel(Unidade unidade, SimNao responsavel);
    long countByUnidadeAndResponsavelAndIdNot(Unidade unidade, SimNao responsavel, Long id);
}
