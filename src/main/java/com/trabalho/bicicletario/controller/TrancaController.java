package com.trabalho.bicicletario.controller;

import com.trabalho.bicicletario.dto.InserirTrancaNaRedeDTO;
import com.trabalho.bicicletario.dto.RemoverTrancaDaRedeDto;
import com.trabalho.bicicletario.dto.TrancaDTO;
import com.trabalho.bicicletario.model.StatusTranca;
import com.trabalho.bicicletario.model.Tranca;
import com.trabalho.bicicletario.service.TrancaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/tranca")
public class TrancaController {
    private final TrancaService trancaService;

    public TrancaController(TrancaService trancaService) {
        this.trancaService = trancaService;
    }

    @GetMapping
    public ResponseEntity<Iterable<TrancaDTO>> getAllTrancas() {
        Iterable<TrancaDTO> listaTrancas = trancaService.getAllTrancas();
        return ResponseEntity.ok(listaTrancas);
    }

    @GetMapping("/{idTranca}")
    public ResponseEntity<TrancaDTO> getTranca(@PathVariable Long idTranca) {
        TrancaDTO tranca = trancaService.getTranca(idTranca);
        return ResponseEntity.ok(tranca);
    }

    @PostMapping
    public ResponseEntity<TrancaDTO> createTranca(@RequestBody Tranca tranca) {
        trancaService.addTranca(tranca);
        TrancaDTO trancaCadastrada = trancaService.getTranca(tranca.getId());
        return ResponseEntity.ok(trancaCadastrada);
    }

    @PutMapping("/{idTranca}")
    public ResponseEntity<TrancaDTO> editTranca(@PathVariable Long idTranca, @RequestBody Tranca tranca) {
        trancaService.updateTranca(idTranca, tranca);
        TrancaDTO trancaAtualizada = trancaService.getTranca(idTranca);
        return ResponseEntity.ok(trancaAtualizada);
    }

    @DeleteMapping("/{idTranca}")
    public ResponseEntity<String> deleteTranca(@PathVariable Long idTranca) {
        trancaService.deleteTranca(idTranca);
        return ResponseEntity.ok("Dados removidos.");
    }

    @PostMapping("/{idTranca}/trancar")
    public ResponseEntity<TrancaDTO> trancar(@PathVariable Long idTranca, @RequestBody(required = false) Long idBicicleta) {
        TrancaDTO tranca = trancaService.trancar(idTranca, idBicicleta);
        return ResponseEntity.ok(tranca);
    }

    @PostMapping("/{idTranca}/destrancar")
    public ResponseEntity<TrancaDTO> destrancar(@PathVariable Long idTranca, @RequestBody(required = false) Long idBicicleta) {
        TrancaDTO tranca = trancaService.destrancar(idTranca, idBicicleta);
        return ResponseEntity.ok(tranca);
    }

    @PostMapping("/integrarNaRede")
    public ResponseEntity<String> integrarNaRede(@RequestBody InserirTrancaNaRedeDTO rede) {
        trancaService.integrarNaRede(rede);
        return ResponseEntity.ok("Dados cadastrados.");
    }

    @PostMapping("/retirarDaRede")
    public ResponseEntity<String> retirarDaRede(@RequestBody RemoverTrancaDaRedeDto rede) {
        trancaService.retirarDaRede(rede);
        return ResponseEntity.ok("Dados cadastrados.");
    }

    @PostMapping("/{idTranca}/status/{acao}")
    public ResponseEntity<TrancaDTO> editStatusTranca(@PathVariable Long idTranca, @PathVariable StatusTranca acao){
        trancaService.alterarStatusTranca(idTranca, acao);
        TrancaDTO trancaAtualizada = trancaService.getTranca(idTranca);
        return ResponseEntity.ok(trancaAtualizada);
    }

}
