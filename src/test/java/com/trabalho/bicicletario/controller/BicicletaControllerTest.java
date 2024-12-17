package com.trabalho.bicicletario.controller;

import com.trabalho.bicicletario.dto.InserirBicicletaNaRedeDTO;
import com.trabalho.bicicletario.dto.RemoverBicicletaDaRedeDTO;
import com.trabalho.bicicletario.model.Bicicleta;
import com.trabalho.bicicletario.Enum.StatusBicicleta;
import com.trabalho.bicicletario.service.BicicletaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BicicletaControllerTest {

    @Mock
    private BicicletaService bicicletaService;

    @InjectMocks
    private BicicletaController bicicletaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBicicletas() {
        List<Bicicleta> bicicletas = Arrays.asList(new Bicicleta(), new Bicicleta());
        when(bicicletaService.getAllBicicletas()).thenReturn(bicicletas);

        ResponseEntity<Iterable<Bicicleta>> response = bicicletaController.getAllBicicletas();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(bicicletas, response.getBody());
        verify(bicicletaService, times(1)).getAllBicicletas();
    }

    @Test
    void testGetBicicleta() {
        Bicicleta bicicleta = new Bicicleta();
        when(bicicletaService.getBicicleta(1L)).thenReturn(bicicleta);

        ResponseEntity<Bicicleta> response = bicicletaController.getBicicleta(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(bicicleta, response.getBody());
        verify(bicicletaService, times(1)).getBicicleta(1L);
    }

    @Test
    void testCreateBicicleta() {
        Bicicleta bicicleta = new Bicicleta();
        bicicleta.setId(1L);
        when(bicicletaService.getBicicleta(1L)).thenReturn(bicicleta);

        ResponseEntity<Bicicleta> response = bicicletaController.createBicicleta(bicicleta);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(bicicleta, response.getBody());
        verify(bicicletaService, times(1)).addBicicleta(bicicleta);
        verify(bicicletaService, times(1)).getBicicleta(1L);
    }

    @Test
    void testIntegrarNaRede() {
        InserirBicicletaNaRedeDTO dto = new InserirBicicletaNaRedeDTO();

        ResponseEntity<String> response = bicicletaController.integrarNaRede(dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Dados cadastrados.", response.getBody());
        verify(bicicletaService, times(1)).integrarNaRede(dto);
    }

    @Test
    void testRetirarDaRede() {
        RemoverBicicletaDaRedeDTO dto = new RemoverBicicletaDaRedeDTO();

        ResponseEntity<String> response = bicicletaController.retirarDaRede(dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Dados cadastrados.", response.getBody());
        verify(bicicletaService, times(1)).retirarDaRede(dto);
    }

    @Test
    void testEditBicicleta() {
        Bicicleta bicicleta = new Bicicleta();
        when(bicicletaService.getBicicleta(1L)).thenReturn(bicicleta);

        ResponseEntity<Bicicleta> response = bicicletaController.editBicicleta(1L, bicicleta);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(bicicleta, response.getBody());
        verify(bicicletaService, times(1)).updateBicicleta(1L, bicicleta);
        verify(bicicletaService, times(1)).getBicicleta(1L);
    }

    @Test
    void testDeleteBicicleta() {
        ResponseEntity<String> response = bicicletaController.deleteBicicleta(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Dados removidos.", response.getBody());
        verify(bicicletaService, times(1)).deleteBicicleta(1L);
    }

    @Test
    void testEditStatusBicicleta() {
        Bicicleta bicicleta = new Bicicleta();
        when(bicicletaService.getBicicleta(1L)).thenReturn(bicicleta);

        ResponseEntity<Bicicleta> response = bicicletaController.editStatusBicicleta(1L, StatusBicicleta.DISPONIVEL);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(bicicleta, response.getBody());
        verify(bicicletaService, times(1)).alterarStatusBicicleta(1L, StatusBicicleta.DISPONIVEL);
        verify(bicicletaService, times(1)).getBicicleta(1L);
    }
}
