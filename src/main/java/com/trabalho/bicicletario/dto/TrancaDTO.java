package com.trabalho.bicicletario.dto;

import com.trabalho.bicicletario.model.StatusTranca;
import com.trabalho.bicicletario.model.Tranca;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrancaDTO {
    private Long numero;
    private String localizacao;
    private String anoDeFabricacao;
    private String modelo;
    private StatusTranca status;

    public TrancaDTO(Tranca tranca) {
        this.numero = tranca.getNumero();
        this.localizacao = tranca.getLocalizacao();
        this.anoDeFabricacao = tranca.getAnoDeFabricacao();
        this.modelo = tranca.getModelo();
        this.status = tranca.getStatus();
    }

}
