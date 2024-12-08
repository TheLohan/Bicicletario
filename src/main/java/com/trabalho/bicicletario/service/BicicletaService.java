package com.trabalho.bicicletario.service;

import com.trabalho.bicicletario.model.Bicicleta;
import com.trabalho.bicicletario.repository.BicicletaRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BicicletaService {
    private final BicicletaRepository bicicletaRepository;

    public BicicletaService(BicicletaRepository bicicletaRepository) {
        this.bicicletaRepository = bicicletaRepository;
    }

    public Iterable<Bicicleta> getAllBicicletas() {
        return bicicletaRepository.findAll();
    }

    public Optional<Bicicleta> getBicicleta(Integer id) {
        return Optional.ofNullable(bicicletaRepository.findById(id)
                .orElseThrow(() -> new EntityExistsException("Não encontrado")));
    }

    public void addBicicleta(Bicicleta bicicleta) {
        if(!bicicleta.dadosValidos()){
            throw new IllegalArgumentException("Dados invalidos.");
        }
        bicicletaRepository.save(bicicleta);
    }

    public void updateBicicleta(Integer id, Bicicleta bicicleta) {
        Bicicleta bicicletaExistente = bicicletaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Não encontrado."));

        bicicletaExistente.setAno(bicicleta.getAno());
        bicicletaExistente.setStatus(bicicleta.getStatus());
        bicicletaExistente.setNumero(bicicleta.getNumero());
        bicicletaExistente.setMarca(bicicleta.getMarca());
        bicicletaExistente.setModelo(bicicleta.getModelo());

        bicicletaRepository.save(bicicletaExistente);

    }

    public void deleteBicicleta(Integer id) {
        bicicletaRepository.deleteById(id);
    }


}
