package br.com.condominial.enums;

public enum TipoMorador {
    PROPRIETARIO("Proprietário"),
    INQUILINO("Inquilino"),
    DEPENDENTE("Dependente");

    private final String descricao;

    TipoMorador(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
