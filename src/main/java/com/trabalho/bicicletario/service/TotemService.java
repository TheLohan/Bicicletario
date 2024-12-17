package com.trabalho.bicicletario.service;

import com.trabalho.bicicletario.enums.ErroDescricao;
import com.trabalho.bicicletario.model.Totem;
import com.trabalho.bicicletario.repository.TotemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TotemService {
    private final TotemRepository totemRepository;

    public TotemService(TotemRepository totemRepository) {
        this.totemRepository = totemRepository;
    }

    public Iterable<Totem> getAllTotems() {
        return totemRepository.findAll();
    }

    public Totem getTotem(Long id) {
        return totemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErroDescricao.NAO_ENCONTRADO.getDescricao()));
    }

    public void addTotem(Totem totem) {
        if(totem.dadosInvalidos())
            throw new IllegalArgumentException(ErroDescricao.DADOS_INVALIDOS.getDescricao());

        totemRepository.save(totem);
    }

    public Totem updateTotem(Long id, Totem totem) {
        Totem totemExistente = getTotem(id);

        if(totem.dadosInvalidos()){
            throw new IllegalArgumentException(ErroDescricao.DADOS_INVALIDOS.getDescricao());
        }

        totemExistente.setDescricao(totem.getDescricao());
        totemExistente.setLocalizacao(totem.getLocalizacao());

        return totemRepository.save(totemExistente);

    }

    public void deleteTotem(Long id) {
        Totem totem = getTotem(id);
        totemRepository.delete(totem);
    }
}
