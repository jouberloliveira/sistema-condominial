package br.com.condominial.domain;

import br.com.condominial.enums.AreaComum;
import br.com.condominial.enums.StatusReserva;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservas_area_comum")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReservaAreaComum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Unidade é obrigatória")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "unidade_id", nullable = false)
    private Unidade unidade;

    @NotNull(message = "Solicitante é obrigatório")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "solicitante_id", nullable = false)
    private Morador solicitante;

    @NotNull(message = "Área é obrigatória")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AreaComum area;

    @NotNull(message = "Data/hora de início é obrigatória")
    @Column(nullable = false)
    private LocalDateTime inicio;

    @NotNull(message = "Data/hora de fim é obrigatória")
    @Column(nullable = false)
    private LocalDateTime fim;

    @NotNull(message = "Status é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusReserva status;

    @Column(length = 500)
    private String observacao;
}
