package br.com.condominial.enums;

public enum StatusOcorrencia {
    ABERTA("Aberta"),
    EM_ANDAMENTO("Em Andamento"),
    RESOLVIDA("Resolvida"),
    CANCELADA("Cancelada");

    private final String descricao;

    StatusOcorrencia(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
