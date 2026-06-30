package com.arenapay.arenapay.service;

import com.arenapay.arenapay.dto.request.CityRequest;
import com.arenapay.arenapay.dto.response.CityResponse;
import com.arenapay.arenapay.exception.custom.NotFoundException;
import com.arenapay.arenapay.mapper.CityMapper;
import com.arenapay.arenapay.model.City;
import com.arenapay.arenapay.model.State;
import com.arenapay.arenapay.repository.CityRepository;
import com.arenapay.arenapay.repository.StateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    @Mock
    private CityRepository cityRepository;
    @Mock
    private StateRepository stateRepository;
    @Mock
    private CityMapper cityMapper;
    @InjectMocks
    private CityService cityService;
    private UUID idDefault;

    @BeforeEach
    void setUp() {
        idDefault = UUID.randomUUID();
    }

    @Test
    void save_returnSuccess() {
        CityRequest request = new CityRequest("Breves", "Pará");
        State state = new State("Pará");
        reflectionsId(state, idDefault);

        City citySaved = new City("Breves", state);

        CityResponse expectedResponse = new CityResponse(idDefault, "Breves", "Pará");


        when(stateRepository.findByNameIgnoreCase("Pará")).thenReturn(Optional.of(state));
        when(cityRepository.existsByNameIgnoreCaseAndStateId("Breves", state.getId())).thenReturn(false);
        when(cityRepository.save(any(City.class))).thenAnswer(invocation -> {
            City cityPassedArguments = invocation.getArgument(0);
            reflectionsId(cityPassedArguments, idDefault);
            return cityPassedArguments;
        });
        when(cityMapper.toResponse(any(City.class))).thenReturn(expectedResponse);

        CityResponse actualResponse = cityService.save(request);

        assertNotNull(actualResponse, "The return value should not be null");
        assertEquals(idDefault, actualResponse.id());
    }

    @Test
    void save_returnNotFoundException_ifStatenotFound() {
        when(stateRepository.findByNameIgnoreCase("Pará")).thenReturn(Optional.empty());
        CityRequest request = new CityRequest("Breves", "Pará");

        NotFoundException exception = assertThrows(NotFoundException.class,()-> cityService.save(request));

        assertEquals("State with name: " + request.state() + " not found", exception.getMessage());
    }

    private void reflectionsId(Object obj, Object id) {
        Field idField = ReflectionUtils.findField(obj.getClass(), "id");
        if (idField != null) {
            ReflectionUtils.makeAccessible(idField);
            ReflectionUtils.setField(idField, obj, id);
        }
    }
}
