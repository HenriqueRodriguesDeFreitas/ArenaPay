package com.arenapay.arenapay.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record StateDto(@Schema(example = "b4279c1c-a4ec-4ae7-827a-59de10c3ea1c", description = "State id") UUID id,
                       @Schema(example = "Pará", description = "State name") String name) {
}
