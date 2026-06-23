package com.arenapay.arenapay.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ErrorResponseDto(@JsonProperty("data_hora") String timestamp,
                               int httpsValue,
                               String error,
                               String description) {
}
