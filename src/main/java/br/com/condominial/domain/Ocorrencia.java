package br.com.condominial.domain;

import br.com.condominial.enums.PrioridadeOcorrencia;
import br.com.condominial.enums.StatusOcorrencia;
import br.com.condominial.enums.TipoOcorrencia;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ocorrencias")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Ocorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "unidade_id")
    private Unidade unidade;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "aberto_por_id")
    private Morador abertoPor;

    @NotNull(message = "Tipo é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoOcorrencia tipo;

    @NotNull(message = "Prioridade é obrigatória")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PrioridadeOcorrencia prioridade;

    @NotNull(message = "Status é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusOcorrencia status;

    @NotBlank(message = "Título é obrigatório")
    @Column(nullable = false, length = 200)
    private String titulo;

    @NotBlank(message = "Descrição é obrigatória")
    @Column(nullable = false, length = 2000)
    private String descricao;

    @NotNull(message = "Data de abertura é obrigatória")
    @Column(nullable = false)
    private LocalDateTime dataAbertura;

    @Column
    private LocalDateTime dataFechamento;
}
