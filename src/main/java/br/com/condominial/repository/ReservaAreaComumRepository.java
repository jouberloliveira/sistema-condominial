package br.com.condominial.repository;

import br.com.condominial.domain.ReservaAreaComum;
import br.com.condominial.enums.AreaComum;
import br.com.condominial.enums.StatusReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservaAreaComumRepository extends JpaRepository<ReservaAreaComum, Long> {

    @Query("""
        SELECT r FROM ReservaAreaComum r
        WHERE r.area = :area
        AND r.status = :status
        AND r.id <> :excludeId
        AND r.inicio < :fim
        AND r.fim > :inicio
    """)
    List<ReservaAreaComum> findConflitos(
        @Param("area") AreaComum area,
        @Param("status") StatusReserva status,
        @Param("inicio") LocalDateTime inicio,
        @Param("fim") LocalDateTime fim,
        @Param("excludeId") Long excludeId
    );
}
