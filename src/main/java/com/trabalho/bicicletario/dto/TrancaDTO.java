package com.trabalho.bicicletario.dto;

import com.trabalho.bicicletario.Enum.StatusTranca;
import com.trabalho.bicicletario.model.Tranca;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrancaDTO {
    private Long id;
    private String localizacao;
    private String anoDeFabricacao;
    private String modelo;
    private StatusTranca status;
    private Integer bicicleta;

    public TrancaDTO(Tranca tranca) {
        this.id = tranca.getId();
        this.localizacao = tranca.getLocalizacao();
        this.anoDeFabricacao = tranca.getAnoDeFabricacao();
        this.modelo = tranca.getModelo();
        this.status = tranca.getStatus();
        this.bicicleta = tranca.getBicicletaDaTranca();
    }

}
