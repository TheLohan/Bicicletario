package com.trabalho.bicicletario.controller;

import com.trabalho.bicicletario.dto.TrancaDTO;
import com.trabalho.bicicletario.model.Bicicleta;
import com.trabalho.bicicletario.model.Totem;
import com.trabalho.bicicletario.service.BicicletaService;
import com.trabalho.bicicletario.service.TotemService;
import com.trabalho.bicicletario.service.TrancaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/totem")
public class TotemController {
    private final TotemService totemService;
    private final TrancaService trancaService;
    private final BicicletaService bicicletaService;

    public TotemController(TotemService totemService, TrancaService trancaService, BicicletaService bicicletaService) {
        this.totemService = totemService;
        this.trancaService = trancaService;
        this.bicicletaService = bicicletaService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Totem>> getAllTotems() {
        return ResponseEntity.ok(totemService.getAllTotems());
    }

    @PostMapping
    public ResponseEntity<Totem> createTotem(@RequestBody Totem totem) {
        totemService.addTotem(totem);
        Totem totemCadastrado = totemService.getTotem(totem.getId());
        return ResponseEntity.ok(totemCadastrado);
    }

    @PutMapping("/{idTotem}")
    public ResponseEntity<Totem> updateTotem(@PathVariable Long idTotem, @RequestBody Totem totem) {
        Totem totemAtualizado = totemService.updateTotem(idTotem, totem);
        return ResponseEntity.ok(totemAtualizado);
    }

    @DeleteMapping("/{idTotem}")
    public ResponseEntity<String> deleteTotem(@PathVariable Long idTotem) {
        totemService.deleteTotem(idTotem);
        return ResponseEntity.ok("Dados removidos.");
    }

    @GetMapping("/{idTotem}/trancas")
    public ResponseEntity<List<TrancaDTO>> listarTrancasPorTotem(@PathVariable Long idTotem) {
        List<TrancaDTO> trancas = trancaService.getTrancasByTotem(idTotem);
        return ResponseEntity.ok(trancas);
    }

    @GetMapping("/{idTotem}/bicicletas")
    public ResponseEntity<List<Bicicleta>> listarBicicletasPorTotem(@PathVariable Long idTotem) {
        List<Bicicleta> bicicletas = bicicletaService.getBicicletasByTotem(idTotem);
        return ResponseEntity.ok(bicicletas);
    }

}
