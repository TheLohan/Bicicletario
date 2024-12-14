package com.trabalho.bicicletario.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErroWrapper {
    @JsonProperty("codigo")
    private int codigo;

    @JsonProperty("mensagem")
    private String mensagem;

    public ErroWrapper() {}

    public ErroWrapper(int codigo, String mensagem) {
        this.codigo = codigo;
        this.mensagem = mensagem;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
