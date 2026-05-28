package br.com.condominial.enums;

public enum SimNao {
    SIM("Sim"),
    NAO("Não");

    private final String descricao;

    SimNao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
