package com.trabalho.bicicletario.service;


import com.trabalho.bicicletario.model.Totem;
import com.trabalho.bicicletario.repository.TotemRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TotemService {
    private final TotemRepository totemRepository;

    public TotemService(TotemRepository totemRepository) {
        this.totemRepository = totemRepository;
    }

    public Iterable<Totem> getAllTotems() {
        return totemRepository.findAll();
    }

    public Optional<Totem> getTotem(long id) {
        return totemRepository.findById(id);
    }

    public void addTotem(Totem totem) {
        totemRepository.save(totem);
    }

    public void updateTotem(long id) {

    }

    public void deleteTotem(long id) {
        totemRepository.deleteById(id);
    }
}
