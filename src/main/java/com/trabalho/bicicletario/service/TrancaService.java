package com.trabalho.bicicletario.service;

import com.trabalho.bicicletario.config.Email;
import com.trabalho.bicicletario.dto.InserirTrancaNaRedeDTO;
import com.trabalho.bicicletario.dto.RemoverTrancaDaRedeDto;
import com.trabalho.bicicletario.model.*;
import com.trabalho.bicicletario.repository.TotemTrancaRepository;
import com.trabalho.bicicletario.repository.TrancaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrancaService {
    private final TrancaRepository trancaRepository;
    private final TotemService totemService;
    private final TotemTrancaRepository totemTrancaRepository;

    public TrancaService(TrancaRepository trancaRepository, TotemService totemService, TotemTrancaRepository totemTrancaRepository) {
        this.trancaRepository = trancaRepository;
        this.totemService = totemService;
        this.totemTrancaRepository = totemTrancaRepository;
    }

    public Tranca getTranca(Long id) {
        return trancaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Não encontrado"));
    }

    public List<Tranca> GetByTotemId(Long idTotem) {
        return trancaRepository.findAllByTotemId(idTotem);
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

    public List<Tranca> getTrancasByTotem(Long idTotem) {
        List<Tranca> trancas = trancaRepository.findAllByTotemId(idTotem);
        return trancas.stream()
                .map(Tranca::new)
                .collect(Collectors.toList());
    }

    public void trancar(Long idTranca, Long idBicicleta) {
        Tranca tranca = getTranca(idTranca);
        tranca.setStatus(StatusTranca.LIVRE);
        trancaRepository.save(tranca);

    }

    public void destrancar(Long idTranca, Long idBicicleta) {
        Tranca tranca = getTranca(idTranca);
        tranca.setStatus(StatusTranca.OCUPADA);
        trancaRepository.save(tranca);
    }

    public void integrarNaRede(InserirTrancaNaRedeDTO rede ){

        Tranca tranca = getTranca(rede.getIdTranca());
        Totem totem = totemService.getTotem(rede.getIdTotem());

        if(tranca.getStatus() != StatusTranca.NOVA && tranca.getStatus() != StatusTranca.EM_REPARO)
            throw new IllegalArgumentException("Status inválido da tranca.");

        tranca.setTotem(totem);
        tranca.setStatus(StatusTranca.LIVRE);
        trancaRepository.save(tranca);

        Email.enivarEmail();

        totemTrancaRepository.save(
                new TotemTranca(
                        tranca.getNumero(),
                        rede.getIdFuncionario(),
                        tranca.getNumero(),
                        LocalDateTime.now(),
                        "INSERIR NA REDE",
                        null
                )
        );


    }

    public void retirarDaRede(RemoverTrancaDaRedeDto rede ){

        Totem totem = totemService.getTotem(rede.getIdTotem());
        Tranca tranca = getTranca(rede.getIdTranca());

        if(tranca.getBicicleta() != null){
            throw new IllegalArgumentException("Tranca possui bicicleta.");
        }

        if(rede.getStatusAcaoReparador().equalsIgnoreCase("reparo")){
           tranca.setStatus(StatusTranca.EM_REPARO);
           trancaRepository.save(tranca);
        }
        else if (rede.getStatusAcaoReparador().equalsIgnoreCase("aposentadoria")){
            tranca.setStatus(StatusTranca.APOSENTADA);
            trancaRepository.save(tranca);
        }


        tranca.setTotem(null);
        trancaRepository.save(tranca);

        totemTrancaRepository.save(
                new TotemTranca(
                        tranca.getNumero(),
                        rede.getIdFuncionario(),
                        tranca.getNumero(),
                        LocalDateTime.now(),
                        "REMOVER DA REDE",
                        rede.getStatusAcaoReparador()
                )
        );

        Email.enivarEmail();

    }

}
