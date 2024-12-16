package com.trabalho.bicicletario.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErroWrapper {
    @JsonProperty("codigo")
    private int codigo;

    @JsonProperty("mensagem")
    private String mensagem;

    public ErroWrapper(int codigo, String mensagem) {
        this.codigo = codigo;
        this.mensagem = mensagem;
    }

}
