package com.arenapay.arenapay.controller;

import com.arenapay.arenapay.dto.response.StateResponseDto;
import com.arenapay.arenapay.service.StateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("states")
public class StateController {

    private final StateService stateService;
    private static final Logger log = LoggerFactory.getLogger(StateController.class);

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping
    public ResponseEntity<List<StateResponseDto>> findAll() {
        log.info("API: requisição GET /states recebida.");
        List<StateResponseDto> responses = stateService.findAll();
        log.info("API: Requisição processada e enviada para o cliente.");
        return ResponseEntity.ok(responses);
    }
}
