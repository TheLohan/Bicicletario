package com.trabalho.bicicletario.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Totem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String localizacao;
    private String descricao;

    public boolean dadosInvalidos(){
        return localizacao == null || descricao == null;
    }
}
