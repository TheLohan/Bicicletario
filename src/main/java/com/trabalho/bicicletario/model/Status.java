package com.trabalho.bicicletario.model;

public enum Status {
    DISPONIVEL("Disponível"),
    EM_USO("Em uso"),
    REPARO_SOLICITADO("Reparo solicitado"),
    EM_REPARO("Em reparo"),
    APOSENTADA("Aposentada");

    private Status(String descricao) {
    }

}
