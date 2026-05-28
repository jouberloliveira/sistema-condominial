package br.com.condominial.enums;

public enum TipoVisitante {
    VISITA("Visita"),
    PRESTADOR("Prestador de Serviço"),
    ENTREGADOR("Entregador");

    private final String descricao;

    TipoVisitante(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
