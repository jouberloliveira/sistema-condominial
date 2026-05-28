package br.com.condominial.repository;

import br.com.condominial.domain.Ocorrencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {
    List<Ocorrencia> findByUnidadeId(Long unidadeId);
}
