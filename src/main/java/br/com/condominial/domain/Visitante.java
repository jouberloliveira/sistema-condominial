package br.com.condominial.domain;

import br.com.condominial.enums.SimNao;
import br.com.condominial.enums.TipoVisitante;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "visitantes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Visitante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Unidade é obrigatória")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "unidade_id", nullable = false)
    private Unidade unidade;

    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false, length = 150)
    private String nome;

    @Column(length = 30)
    private String documento;

    @Column(length = 20)
    private String telefone;

    @NotNull(message = "Tipo é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVisitante tipo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autorizado_por_id")
    private Morador autorizadoPor;

    @NotNull(message = "Ativo é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SimNao ativo;
}
