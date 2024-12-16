package com.trabalho.bicicletario.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Totem {
    @Id
    private Long id;
    private String localizacao;
    private String descricao;

    @OneToMany(mappedBy = "totem", cascade = CascadeType.ALL)
    private List<Tranca> trancas;

    public Totem(Long id, String localizacao, String descricao) {
        this.id = id;
        this.localizacao = localizacao;
        this.descricao = descricao;
    }

    public boolean dadosValidos(){
        return localizacao != null && descricao != null;
    }
}
