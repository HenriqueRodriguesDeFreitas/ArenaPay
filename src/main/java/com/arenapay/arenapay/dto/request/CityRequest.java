package com.arenapay.arenapay.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CityRequest(@Schema(name = "name", examples = "Breves") @NotBlank String name,
                          @Schema(name = "state", example = "Pará") @NotBlank String state) {
}
