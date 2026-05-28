package br.com.condominial.domain;

import br.com.condominial.enums.SimNao;
import br.com.condominial.enums.TipoMorador;
import br.com.condominial.validation.ValidCPF;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "moradores")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Morador {

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

    @NotBlank(message = "CPF é obrigatório")
    @ValidCPF
    @Column(unique = true, nullable = false, length = 14)
    private String cpf;

    @Column(length = 20)
    private String telefone;

    @Email(message = "E-mail inválido")
    @Column(length = 150)
    private String email;

    @NotNull(message = "Tipo é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMorador tipo;

    @NotNull(message = "Responsável é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SimNao responsavel;
}
