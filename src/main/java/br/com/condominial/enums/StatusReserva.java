package br.com.condominial.enums;

public enum StatusReserva {
    SOLICITADA("Solicitada"),
    APROVADA("Aprovada"),
    CANCELADA("Cancelada"),
    CONCLUIDA("Concluída");

    private final String descricao;

    StatusReserva(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
