package br.com.condominial.repository;

import br.com.condominial.domain.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnidadeRepository extends JpaRepository<Unidade, Long> {
    Optional<Unidade> findByBlocoAndNumero(String bloco, String numero);
    boolean existsByBlocoAndNumeroAndIdNot(String bloco, String numero, Long id);
}
