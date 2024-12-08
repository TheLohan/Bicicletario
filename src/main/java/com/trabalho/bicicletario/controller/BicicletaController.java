package com.trabalho.bicicletario.controller;

import com.trabalho.bicicletario.dto.RespostaWrapper;
import com.trabalho.bicicletario.model.Bicicleta;
import com.trabalho.bicicletario.service.BicicletaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/bicicleta")
public class BicicletaController {

    private final BicicletaService bicicletaService;

    public BicicletaController(BicicletaService bicicletaService) {
        this.bicicletaService = bicicletaService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Bicicleta>> getAllBicicletas() {
        return ResponseEntity.ok(bicicletaService.getAllBicicletas());
    }

    @GetMapping("/{idBicicleta}")
    public ResponseEntity<RespostaWrapper<Optional<Bicicleta>>> getBicicleta(@PathVariable Integer idBicicleta) {
        Optional<Bicicleta> bicicleta = bicicletaService.getBicicleta(idBicicleta);
        return ResponseEntity.ok(new RespostaWrapper<>(bicicleta, "Bicicleta encontrada."));
    }

    @PostMapping
    public ResponseEntity<String> addBicicleta(@RequestBody Bicicleta bicicleta) {
        bicicletaService.addBicicleta(bicicleta);
        return ResponseEntity.ok("Dados cadastrados.");
    }

    @PutMapping("/{idBicicleta}")
    public ResponseEntity editBicicleta(@PathVariable Integer idBicicleta, @RequestBody Bicicleta bicicleta) {
        bicicletaService.updateBicicleta(idBicicleta, bicicleta);
        return ResponseEntity.ok("Dados cadastrados!");
    }

    @DeleteMapping("/{idBicicleta}")
    public ResponseEntity<String> deleteBicicleta(@PathVariable Integer idBicicleta) {
        bicicletaService.deleteBicicleta(idBicicleta);
        return ResponseEntity.ok("Bicicleta deletada com sucesso!");
    }

}
