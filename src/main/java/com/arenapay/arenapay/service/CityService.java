package com.arenapay.arenapay.service;

import com.arenapay.arenapay.dto.request.CityRequest;
import com.arenapay.arenapay.dto.response.CityResponse;
import com.arenapay.arenapay.exception.custom.EntityExistingException;
import com.arenapay.arenapay.exception.custom.NotFoundException;
import com.arenapay.arenapay.mapper.CityMapper;
import com.arenapay.arenapay.model.City;
import com.arenapay.arenapay.model.State;
import com.arenapay.arenapay.repository.CityRepository;
import com.arenapay.arenapay.repository.StateRepository;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

import static com.arenapay.arenapay.util.LoggerPerformance.LogPerformance.calculateTimeTaken;
import static com.arenapay.arenapay.util.LoggerPerformance.LogPerformance.logPerformanceTwoSeconds;
import static com.arenapay.arenapay.util.StringUtils.NormalizeTrim.normalizeTrim;

@Service
@Transactional(readOnly = true)
public class CityService {

    private final CityRepository cityRepository;
    private final StateRepository stateRepository;
    private final CityMapper cityMapper;
    private static final Logger log = LoggerFactory.getLogger(CityService.class);
    private static final String CLASS_NAME = CityService.class.getSimpleName();

    public CityService(CityRepository cityRepository, StateRepository stateRepository, CityMapper cityMapper) {
        this.cityRepository = cityRepository;
        this.stateRepository = stateRepository;
        this.cityMapper = cityMapper;
    }

    @Transactional
    public CityResponse save(CityRequest cityRequest) {
        log.info("Starting city creation process for: {}", cityRequest.name());

        Instant start = Instant.now();

        var stateName = normalizeTrim(cityRequest.state());
        var cityName = normalizeTrim(cityRequest.name());

        var stateReturn = getStateByNameReturn(stateName);

        if (isCityInState(cityName, stateReturn)) {
            String msgError = String.format("State '%s' already has a record of a city registered with name '%s'", stateReturn.getName(), cityName);
            throw new EntityExistingException(msgError);
        }

        var newCity = new City(cityName, stateReturn);
        var citySaved = cityRepository.save(newCity);
        log.info("City saved! Id: {}, name: {}, references state: {}", citySaved.getId(), citySaved.getName(), stateName);

        long timeTaken = calculateTimeTaken(start);
        logPerformanceTwoSeconds(CLASS_NAME, "save", timeTaken, 1);

        log.info("Returned to user with success!");
        return cityMapper.toResponse(citySaved);
    }

    @Transactional
    public CityResponse update(UUID cityId, CityRequest request) {
        log.info("Starting city update process for ID: {} with new data: {}", cityId, request.name());
        Instant start = Instant.now();

        var cityName = normalizeTrim(request.name());
        var stateName = normalizeTrim(request.state());

        var cityReturned = cityRepository.findById(cityId).orElseThrow(() -> new NotFoundException("City with id: " + cityId + " not found"));
        var stateReturned = getStateByNameReturn(stateName);


        boolean nameChanged = !cityReturned.getName().equalsIgnoreCase(cityName);
        boolean stateChanged = !cityReturned.getState().getId().equals(stateReturned.getId());

        if ((nameChanged || stateChanged) && isCityInState(cityName, stateReturned)) {
            String msgError = String.format("State '%s' already has another city registered with the name '%s'", stateReturned.getName(), cityName);
            throw new EntityExistingException(msgError);
        }

        cityReturned.setName(cityName);
        cityReturned.setState(stateReturned);

        var citySaved = cityRepository.save(cityReturned);
        log.info("City updated successfully! ID: {}, name: {}, references state: {}", citySaved.getId(), citySaved.getName(), stateName);

        long timeTaken = calculateTimeTaken(start);
        logPerformanceTwoSeconds(CLASS_NAME, "update", timeTaken, 1);

        log.info("Returned to user with success!");
        return cityMapper.toResponse(citySaved);
    }

    private boolean isCityInState(String name, State stateReturned) {
        return cityRepository.existsByNameIgnoreCaseAndStateId(name, stateReturned.getId());
    }


    private @NonNull State getStateByNameReturn(String stateName) {
        return stateRepository.findByNameIgnoreCase(stateName)
            .orElseThrow(() -> new NotFoundException("State with name: " + stateName + " not found"));
    }
}
