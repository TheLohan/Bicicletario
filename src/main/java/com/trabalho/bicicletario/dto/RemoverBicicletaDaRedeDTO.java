package com.trabalho.bicicletario.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoverBicicletaDaRedeDTO {
    private Long idTranca;
    private Long idBicicleta;
    private Long idFuncionario;
    private String statusAcaoReparador;
}
