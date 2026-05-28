package br.com.condominial.enums;

public enum PrioridadeOcorrencia {
    BAIXA("Baixa"),
    MEDIA("Média"),
    ALTA("Alta");

    private final String descricao;

    PrioridadeOcorrencia(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
