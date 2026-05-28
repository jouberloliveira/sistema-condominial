package br.com.condominial.enums;

public enum TipoOcorrencia {
    MANUTENCAO("Manutenção"),
    RECLAMACAO("Reclamação"),
    SUGESTAO("Sugestão"),
    SEGURANCA("Segurança"),
    OUTROS("Outros");

    private final String descricao;

    TipoOcorrencia(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
