package com.arenapay.arenapay.mapper;

import com.arenapay.arenapay.dto.response.CityResponse;
import com.arenapay.arenapay.model.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CityMapper {

    @Mapping(source = "state.name", target = "state")
    CityResponse toResponse(City city);

}
