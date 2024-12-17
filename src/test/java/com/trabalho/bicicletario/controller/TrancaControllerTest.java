package com.trabalho.bicicletario.controller;

import com.trabalho.bicicletario.dto.InserirTrancaNaRedeDTO;
import com.trabalho.bicicletario.dto.RemoverTrancaDaRedeDto;
import com.trabalho.bicicletario.dto.TrancaDTO;
import com.trabalho.bicicletario.enums.StatusTranca;
import com.trabalho.bicicletario.model.Tranca;
import com.trabalho.bicicletario.service.TrancaService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrancaControllerTest {

    @Mock
    private TrancaService trancaService;

    @InjectMocks
    private TrancaController trancaController;

    public TrancaControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTrancas() {
        Tranca tranca1 = new Tranca();
        Tranca tranca2 = new Tranca();
        when(trancaService.getAllTrancas()).thenReturn(Arrays.asList(new TrancaDTO(tranca1), new TrancaDTO(tranca2)));

        ResponseEntity<Iterable<TrancaDTO>> response = trancaController.getAllTrancas();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().spliterator().getExactSizeIfKnown());
    }

    @Test
    void testGetTranca() {
        Tranca tranca = new Tranca();
        when(trancaService.getTranca(1L)).thenReturn(new TrancaDTO(tranca));

        ResponseEntity<TrancaDTO> response = trancaController.getTranca(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(new TrancaDTO(tranca), response.getBody());
    }

    @Test
    void testCreateTranca() {
        Tranca tranca = new Tranca();
        tranca.setId(1L);

        TrancaDTO trancaDTO = new TrancaDTO(tranca);
        when(trancaService.getTranca(1L)).thenReturn(trancaDTO);

        ResponseEntity<TrancaDTO> response = trancaController.createTranca(tranca);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(trancaDTO, response.getBody());
    }

    @Test
    void testEditTranca() {
        Tranca tranca = new Tranca();
        tranca.setId(1L);

        TrancaDTO trancaDTO = new TrancaDTO(tranca);
        when(trancaService.getTranca(1L)).thenReturn(trancaDTO);

        ResponseEntity<TrancaDTO> response = trancaController.editTranca(1L, tranca);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(trancaDTO, response.getBody());
    }

    @Test
    void testDeleteTranca() {
        doNothing().when(trancaService).deleteTranca(1L);

        ResponseEntity<String> response = trancaController.deleteTranca(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Dados removidos.", response.getBody());
    }

    @Test
    void testTrancar() {
        Tranca tranca = new Tranca();
        TrancaDTO trancaDTO = new TrancaDTO(tranca);
        when(trancaService.trancar(1L, 10L)).thenReturn(trancaDTO);

        ResponseEntity<TrancaDTO> response = trancaController.trancar(1L, 10L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(trancaDTO, response.getBody());
    }

    @Test
    void testDestrancar() {
        Tranca tranca = new Tranca();
        TrancaDTO trancaDTO = new TrancaDTO(tranca);
        when(trancaService.destrancar(1L, 10L)).thenReturn(trancaDTO);

        ResponseEntity<TrancaDTO> response = trancaController.destrancar(1L, 10L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(trancaDTO, response.getBody());
    }

    @Test
    void testIntegrarNaRede() {
        InserirTrancaNaRedeDTO dto = new InserirTrancaNaRedeDTO();
        doNothing().when(trancaService).integrarNaRede(dto);

        ResponseEntity<String> response = trancaController.integrarNaRede(dto);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Dados cadastrados.", response.getBody());
    }

    @Test
    void testRetirarDaRede() {
        RemoverTrancaDaRedeDto dto = new RemoverTrancaDaRedeDto();
        doNothing().when(trancaService).retirarDaRede(dto);

        ResponseEntity<String> response = trancaController.retirarDaRede(dto);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Dados cadastrados.", response.getBody());
    }

    @Test
    void testEditStatusTranca() {
        Tranca tranca = new Tranca();
        TrancaDTO trancaDTO = new TrancaDTO(tranca);
        when(trancaService.getTranca(1L)).thenReturn(trancaDTO);
        doNothing().when(trancaService).alterarStatusTranca(1L, StatusTranca.LIVRE);

        ResponseEntity<TrancaDTO> response = trancaController.editStatusTranca(1L, StatusTranca.LIVRE);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(trancaDTO, response.getBody());
    }
}
