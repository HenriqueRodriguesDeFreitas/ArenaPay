package com.arenapay.arenapay.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record CityRequest(@Schema(name = "Breves", examples = "City name") String name, @Schema(name = "Pará", example = "State name") String state) {
}
