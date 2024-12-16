package com.trabalho.bicicletario.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "totemtranca")
public class TotemTranca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_tranca")
    private Long idTranca;

    @Column(name = "id_totem")
    private Long idTotem;

    @Column(name = "id_funcionario")
    private Long idFuncionario;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @Column(name = "acao")
    private String acao;

    @Column(name = "status_acao_reparador")
    private String statusAcaoReparador;

    public TotemTranca(Long idTranca, Long idBicicleta, Long idFuncionario, LocalDateTime dataHora, String acao, String statusAcaoReparador) {
        this.idTranca = idTranca;
        this.idTotem = idBicicleta;
        this.idFuncionario = idFuncionario;
        this.dataHora = dataHora;
        this.acao = acao;
        this.statusAcaoReparador = statusAcaoReparador;
    }
}
