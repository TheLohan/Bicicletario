package com.trabalho.bicicletario.Enum;

import lombok.Getter;

@Getter
public enum ErroDescricao {
    NAO_ENCONTRADO("Não Encontrado."),
    DADOS_INVALIDOS("Dados inválidos.");

    private final String descricao;

    ErroDescricao(String descricao) {
        this.descricao = descricao;
    }

}