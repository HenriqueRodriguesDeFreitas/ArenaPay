package com.arenapay.arenapay.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record CityResponse(@Schema(name = "id", example = "b4279c1c-a4ec-4ae7-827a-59de10c3ea1c") UUID id,
                           @Schema(name = "name", example = "Breves") String name,
                           @Schema(name = "state", example = "Pará") String state) {
}
