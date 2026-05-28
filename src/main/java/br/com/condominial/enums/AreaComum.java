package br.com.condominial.enums;

public enum AreaComum {
    CHURRASQUEIRA("Churrasqueira"),
    SALAO_FESTAS("Salão de Festas"),
    QUADRA("Quadra"),
    PISCINA("Piscina");

    private final String descricao;

    AreaComum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
