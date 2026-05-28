package br.com.condominial.repository;

import br.com.condominial.domain.Unidade;
import br.com.condominial.domain.Visitante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitanteRepository extends JpaRepository<Visitante, Long> {
    List<Visitante> findByUnidade(Unidade unidade);
    List<Visitante> findByUnidadeId(Long unidadeId);
}
