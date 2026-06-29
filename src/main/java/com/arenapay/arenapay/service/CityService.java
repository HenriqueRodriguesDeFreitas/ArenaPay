package com.arenapay.arenapay.service;

import com.arenapay.arenapay.dto.request.CityRequest;
import com.arenapay.arenapay.dto.response.CityResponse;
import com.arenapay.arenapay.exception.custom.EntityExistingException;
import com.arenapay.arenapay.exception.custom.NotFoundException;
import com.arenapay.arenapay.mapper.CityMapper;
import com.arenapay.arenapay.model.City;
import com.arenapay.arenapay.repository.CityRepository;
import com.arenapay.arenapay.repository.StateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CityService {

    private final CityRepository cityRepository;
    private final StateRepository stateRepository;
    private final CityMapper cityMapper;
    private static final Logger log = LoggerFactory.getLogger(CityService.class);

    public CityService(CityRepository cityRepository, StateRepository stateRepository,CityMapper cityMapper) {
        this.cityRepository = cityRepository;
        this.stateRepository = stateRepository;
        this.cityMapper = cityMapper;
    }

    @Transactional
    public CityResponse save(CityRequest cityRequest) {
        log.info("Initialized method save from class: CityService");

        log.info("Normalizing Strings");
        var stateName = normalizeTrim(cityRequest.state());
        var cityName = normalizeTrim(cityRequest.name());
        log.info("Normalization success");

        log.info("Initializing find state by name: {}", stateName);
        var stateReturn = stateRepository.findByNameIgnoreCase(stateName)
            .orElseThrow(()-> new NotFoundException("State with name: " + stateName + " not found"));
        log.info("State returned!");

        log.info("Initializing verification if state: {} already has city: {}", stateName, cityName);
        if(cityRepository.findByNameIgnoreCaseAndStateById(cityName, stateReturn.getId())) {
            String msgError = String.format("State '%s' already has a record of a city registered with name '%s'", stateReturn.getName(), cityName);
            throw new EntityExistingException(msgError);
        }

        var newCity = new City(cityName, stateReturn);
        var citySaved = cityRepository.save(newCity);
        log.info("City saved! Id: {}, name: {}, references state: {}", citySaved.getId(), citySaved.getName(), stateName);

        log.info("Returned to user with success!");
        return cityMapper.toResponse(citySaved);
    }

    private String normalizeTrim (String text) {
        return text.trim();
    }
}
