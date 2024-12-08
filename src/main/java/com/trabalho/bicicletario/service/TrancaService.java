package com.trabalho.bicicletario.service;

import com.trabalho.bicicletario.model.Tranca;
import com.trabalho.bicicletario.repository.TrancaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrancaService {
    private final TrancaRepository trancaRepository;

    public TrancaService(TrancaRepository trancaRepository) {
        this.trancaRepository = trancaRepository;
    }

    public Optional<Tranca> getTranca(Long id) {
        return trancaRepository.findById(id);
    }

    public Iterable<Tranca> getAllTrancas(){
        return trancaRepository.findAll();
    }

    public Tranca addTranca(Tranca tranca) {
        return trancaRepository.save(tranca);
    }

    public void updateTranca(Long id, Tranca tranca) {

    }

    public void deleteTranca(Long id) {
        trancaRepository.deleteById(id);
    }



}
