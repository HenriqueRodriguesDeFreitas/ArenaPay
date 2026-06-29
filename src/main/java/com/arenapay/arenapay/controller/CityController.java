package com.arenapay.arenapay.controller;

import com.arenapay.arenapay.dto.request.CityRequest;
import com.arenapay.arenapay.dto.response.CityResponse;
import com.arenapay.arenapay.dto.response.ErrorResponseDto;
import com.arenapay.arenapay.service.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cities")
@Tag(name = "City", description = "Controller for city routes")
@Validated
public class CityController {

    private final CityService cityService;
    private final Logger log = LoggerFactory.getLogger(CityController.class);
    private static final String TYPE_JSON = "application/json";

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping
    @Operation(summary = "Save city", description = "Save cities in the database.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "CREATED", content = @Content(mediaType = TYPE_JSON,
        schema = @Schema(implementation = CityResponse.class),
        examples = {@ExampleObject(name = "City Created.", value = """
            {"id" : "b4279c1c-a4ec-4ae7-827a-59de10c3ea1c",
            "name" : "Breves",
            "state" : "Pará"
            }
            """)})),
        // Erro 1: Validação dos campos (ex: nome em branco)
        @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input data", content = @Content(mediaType = TYPE_JSON,
            schema = @Schema(implementation = ErrorResponseDto.class),
            examples = {@ExampleObject(name = "Validation Error Example", value = """
                {
                  "title": "Constraint Violation",
                  "status": 400,
                  "detail": "name: Name parameter cannot be blank",
                  "timestamp": "2026-06-29T20:36:24.003"
                }
                """)})),

        // Erro 2: Estado não encontrado
        @ApiResponse(responseCode = "404", description = "Not Found - State does not exist", content = @Content(mediaType = TYPE_JSON,
            schema = @Schema(implementation = ErrorResponseDto.class),
            examples = {@ExampleObject(name = "State Not Found Example", value = """
                {
                  "title": "Resource Not Found",
                  "status": 404,
                  "detail": "State with name: Pará not found",
                  "timestamp": "2026-06-29T20:36:24.003"
                }
                """)})),

        // Erro 3: Cidade já cadastrada (Duplicação)
        @ApiResponse(responseCode = "409", description = "Conflict - City already registered in this state", content = @Content(mediaType = TYPE_JSON,
            schema = @Schema(implementation = ErrorResponseDto.class),
            examples = {@ExampleObject(name = "Duplication Error Example", value = """
                {
                  "title": "Entity Existing",
                  "status": 409,
                  "detail": "State 'Pará' already has a record of a city registered with name 'Breves'",
                  "timestamp": "2026-06-29T20:36:24.003"
                }
                """)}))

    })
    public ResponseEntity<CityResponse> save(@Valid @RequestBody CityRequest request) {
        log.info("API: POST /cities request received");
        var citySaved = cityService.save(request);
        log.info("API: Request processed and sent to client.");
        return ResponseEntity.status(HttpStatus.CREATED).body(citySaved);
    }
}
