package com.arenapay.arenapay.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record CityResponse(@Schema(name = "id", example = "City id") UUID id,
                           @Schema(name = "name", description = "City name") String name,
                           @Schema(name = "state", example = "State name") String state) {
}
