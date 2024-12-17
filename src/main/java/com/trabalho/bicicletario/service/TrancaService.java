package com.trabalho.bicicletario.service;

import com.trabalho.bicicletario.Enum.ErroDescricao;
import com.trabalho.bicicletario.config.Email;
import com.trabalho.bicicletario.dto.InserirTrancaNaRedeDTO;
import com.trabalho.bicicletario.dto.RemoverTrancaDaRedeDto;
import com.trabalho.bicicletario.dto.TrancaDTO;
import com.trabalho.bicicletario.Enum.StatusTranca;
import com.trabalho.bicicletario.model.Totem;
import com.trabalho.bicicletario.model.TotemTranca;
import com.trabalho.bicicletario.model.Tranca;
import com.trabalho.bicicletario.repository.TotemTrancaRepository;
import com.trabalho.bicicletario.repository.TrancaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class TrancaService {
    private final TrancaRepository trancaRepository;
    private final TotemService totemService;
    private final TotemTrancaRepository totemTrancaRepository;

    public TrancaService(TrancaRepository trancaRepository, TotemService totemService, TotemTrancaRepository totemTrancaRepository ) {
        this.trancaRepository = trancaRepository;
        this.totemService = totemService;
        this.totemTrancaRepository = totemTrancaRepository;
    }

    public TrancaDTO getTranca(Long id) {
        Tranca tranca = trancaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErroDescricao.NAO_ENCONTRADO.getDescricao()));
        return new TrancaDTO(tranca);
    }

    public List<Tranca> getByTotemId(Long idTotem) {
        return trancaRepository.findByTotemId(idTotem);
    }

    public Iterable<TrancaDTO> getAllTrancas(){
        Iterable<Tranca> trancas = trancaRepository.findAll();
        return StreamSupport.stream(trancas.spliterator(), false)
                .map(TrancaDTO::new)
                .toList();
    }

    public void addTranca(Tranca tranca) {
        if(tranca.dadosInvalidos())
            throw new IllegalArgumentException(ErroDescricao.DADOS_INVALIDOS.getDescricao());

        if(trancaRepository.existsByNumero(tranca.getNumero()))
            throw new IllegalArgumentException(ErroDescricao.DADOS_INVALIDOS.getDescricao());

        trancaRepository.save(tranca);
    }

    public void updateTranca(Long id, Tranca tranca) {
        Tranca trancaExistente = trancaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Não encontrado"));

        if(tranca.dadosInvalidos()){
            throw new IllegalArgumentException("Dados invalidos.");
        }

        trancaExistente.setAnoDeFabricacao(tranca.getAnoDeFabricacao());
        trancaExistente.setModelo(tranca.getModelo());
        trancaExistente.setStatus(tranca.getStatus());
        trancaExistente.setLocalizacao(tranca.getLocalizacao());
        trancaExistente.setBicicleta(tranca.getBicicleta());

        trancaRepository.save(trancaExistente);

    }

    public void deleteTranca(Long id) {
        trancaRepository.deleteById(id);
    }

    public List<TrancaDTO> getTrancasByTotem(Long idTotem) {
        List<Tranca> trancas = trancaRepository.findByTotemId(idTotem);
        return trancas.stream()
                .map(TrancaDTO::new)
                .toList();
    }

    public TrancaDTO trancar(Long idTranca, Long idBicicleta) {
        Tranca tranca = trancaRepository.findById(idTranca)
                .orElseThrow(() -> new EntityNotFoundException(ErroDescricao.NAO_ENCONTRADO.getDescricao()));
        tranca.setStatus(StatusTranca.LIVRE);
        trancaRepository.save(tranca);
        return new TrancaDTO(tranca);
    }

    public TrancaDTO destrancar(Long idTranca, Long idBicicleta) {
        Tranca tranca = trancaRepository.findById(idTranca)
                .orElseThrow(() -> new EntityNotFoundException(ErroDescricao.NAO_ENCONTRADO.getDescricao()));
        tranca.setStatus(StatusTranca.OCUPADA);
        trancaRepository.save(tranca);
        return new TrancaDTO(tranca);
    }

    public void integrarNaRede(InserirTrancaNaRedeDTO rede ){

        Tranca tranca = trancaRepository.findById(rede.getIdTranca())
                .orElseThrow(() -> new EntityNotFoundException(ErroDescricao.NAO_ENCONTRADO.getDescricao()));

        Totem totem = totemService.getTotem(rede.getIdTotem());

        if(tranca.getStatus() != StatusTranca.NOVA && tranca.getStatus() != StatusTranca.EM_REPARO)
            throw new IllegalArgumentException("Status inválido da tranca.");

        tranca.setTotem(totem);
        tranca.setStatus(StatusTranca.LIVRE);
        trancaRepository.save(tranca);

        Email.enivarEmail();

        totemTrancaRepository.save(
                new TotemTranca(
                        tranca.getId(),
                        rede.getIdFuncionario(),
                        tranca.getId(),
                        LocalDateTime.now(),
                        "INSERIR NA REDE",
                        null
                )
        );


    }

    public void retirarDaRede(RemoverTrancaDaRedeDto rede ){

        Tranca tranca = trancaRepository.findById(rede.getIdTranca())
                .orElseThrow(() -> new EntityNotFoundException(ErroDescricao.NAO_ENCONTRADO.getDescricao()));

        if(tranca.getBicicleta() != null){
            throw new IllegalArgumentException("Tranca possui bicicleta.");
        }

        if(rede.getStatusAcaoReparador().equalsIgnoreCase("reparo")){
            alterarStatusTranca(tranca, StatusTranca.EM_REPARO);
        }
        else if (rede.getStatusAcaoReparador().equalsIgnoreCase("aposentadoria")){
            alterarStatusTranca(tranca, StatusTranca.APOSENTADA);
        }

        tranca.setTotem(null);
        trancaRepository.save(tranca);

        totemTrancaRepository.save(
                new TotemTranca(
                        tranca.getId(),
                        rede.getIdFuncionario(),
                        tranca.getId(),
                        LocalDateTime.now(),
                        "REMOVER DA REDE",
                        rede.getStatusAcaoReparador()
                )
        );

        Email.enivarEmail();

    }

    public void alterarStatusTranca(Long id, StatusTranca status) {
        Tranca trancaExistente = trancaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErroDescricao.NAO_ENCONTRADO.getDescricao()));

        trancaExistente.setStatus(status);

        trancaRepository.save(trancaExistente);
    }

    public void alterarStatusTranca(Tranca tranca, StatusTranca status) {
        tranca.setStatus(status);
        trancaRepository.save(tranca);
    }

}
