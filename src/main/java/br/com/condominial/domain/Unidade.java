package br.com.condominial.domain;

import br.com.condominial.enums.SituacaoUnidade;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "unidades", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"bloco", "numero"})
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Unidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Bloco é obrigatório")
    @Column(nullable = false, length = 10)
    private String bloco;

    @NotBlank(message = "Número é obrigatório")
    @Column(nullable = false, length = 10)
    private String numero;

    @Column(length = 5)
    private String andar;

    @Column(length = 10)
    private String vaga;

    @NotNull(message = "Situação é obrigatória")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SituacaoUnidade situacao;

    public String getIdentificacao() {
        return numero + "-" + bloco;
    }
}
