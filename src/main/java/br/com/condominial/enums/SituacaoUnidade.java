package br.com.condominial.enums;

public enum SituacaoUnidade {
    OCUPADA("Ocupada"),
    DESOCUPADA("Desocupada"),
    EM_REFORMA("Em Reforma");

    private final String descricao;

    SituacaoUnidade(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
