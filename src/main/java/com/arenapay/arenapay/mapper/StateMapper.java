package com.arenapay.arenapay.mapper;

import com.arenapay.arenapay.dto.response.StateResponseDto;
import com.arenapay.arenapay.model.State;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StateMapper {

    StateResponseDto toResponse(State state);
}
