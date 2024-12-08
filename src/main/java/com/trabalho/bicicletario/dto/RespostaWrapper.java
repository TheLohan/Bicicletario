package com.trabalho.bicicletario.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RespostaWrapper<T> {
    private T data;
    private String mensagem;

    public RespostaWrapper(T data, String mensagem) {
        this.data = data;
        this.mensagem = mensagem;
    }

}
