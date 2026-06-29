package com.arenapay.arenapay.controller;

import com.arenapay.arenapay.dto.response.StateResponseDto;
import com.arenapay.arenapay.service.StateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("states")
@Tag(name = "State", description = "Controller for state routes")
@Validated
public class StateController {

    private final StateService stateService;
    private static final Logger log = LoggerFactory.getLogger(StateController.class);
    private static final String TYPE_JSON = "application/json";

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping
    @Operation(summary = "Find all states", description = "Find all states from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "States returned successfully", content = @Content(mediaType = TYPE_JSON,
                    schema = @Schema(implementation = StateResponseDto.class),
                    examples = {@ExampleObject(name = "States returned successfully", value = """
                            [{
                            "id" : "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                            "name" : "Pará"},
                            {
                            "id" : "4fa85f64-5717-4569-b3fc-2c963f66afa2",
                            "name" : "Paraná"
                            }]
                            """)}))
    })
    public ResponseEntity<List<StateResponseDto>> findAll() {
        log.info("API: GET /states request received.");
        List<StateResponseDto> responses = stateService.findAll();
        log.info("API: Request processed and sent to client.");
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/search")
    @Operation(summary = "Find state by name", description = "Find a specific state by its name (case insensitive)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "State found successfully", content = @Content(mediaType = TYPE_JSON,
                    schema = @Schema(implementation = StateResponseDto.class),
                    examples = {@ExampleObject(name = "State found successfully", value = """
                            {
                            "id" : "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                            "name" : "Pará"
                            }
                            """)})),
            @ApiResponse(responseCode = "404", description = "State not found", content = @Content(mediaType = TYPE_JSON))
    })
    public ResponseEntity<StateResponseDto> findByName(
            @Parameter(description = "Name of the state to search", example = "Pará")
            @RequestParam @NotBlank String name) {

        log.info("API: GET /states/search request received for name: '{}'.", name);
        StateResponseDto response = stateService.findByName(name);
        log.info("API: State '{}' found and sent to client.", name);
        return ResponseEntity.ok(response);
    }
}
