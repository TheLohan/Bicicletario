package com.trabalho.bicicletario.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoverTrancaDaRedeDto {
    private Long idTotem;
    private Long idTranca;
    private Long idFuncionario;
    private String statusAcaoReparador;
}
