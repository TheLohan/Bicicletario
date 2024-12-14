package com.trabalho.bicicletario.service;

import com.trabalho.bicicletario.model.Tranca;
import com.trabalho.bicicletario.repository.TrancaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrancaService {
    private final TrancaRepository trancaRepository;

    public TrancaService(TrancaRepository trancaRepository) {
        this.trancaRepository = trancaRepository;
    }

    public Tranca getTranca(Long id) {
        return trancaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Não encontrado"));
    }

    public Iterable<Tranca> getAllTrancas(){
        return trancaRepository.findAll();
    }

    public void addTranca(Tranca tranca) {
        if(!tranca.dadosValidos())
            throw new IllegalArgumentException("Dados invalidos.");

        trancaRepository.save(tranca);
    }

    public void updateTranca(Long id, Tranca tranca) {
        Tranca trancaExistente = getTranca(id);

        if(!tranca.dadosValidos()){
            throw new IllegalArgumentException("Dados invalidos.");
        }

        trancaExistente.setAnoDeFabricacao(tranca.getAnoDeFabricacao());
        trancaExistente.setNumero(tranca.getNumero());
        trancaExistente.setModelo(tranca.getModelo());
        trancaExistente.setStatus(tranca.getStatus());
        trancaExistente.setLocalizacao(tranca.getLocalizacao());
        trancaExistente.setBicicleta(tranca.getBicicleta());

        trancaRepository.save(trancaExistente);

    }

    public void deleteTranca(Long id) {
        Tranca tranca = getTranca(id);
        trancaRepository.delete(tranca);
    }

}
