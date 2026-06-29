package com.arenapay.arenapay.service;

import com.arenapay.arenapay.dto.response.StateResponseDto;
import com.arenapay.arenapay.exception.custom.NotFoundException;
import com.arenapay.arenapay.mapper.StateMapper;
import com.arenapay.arenapay.repository.StateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

import static com.arenapay.arenapay.util.LoggerPerformance.LogPerformance.calculateTimeTaken;
import static com.arenapay.arenapay.util.LoggerPerformance.LogPerformance.logPerformanceTwoSeconds;

@Service
@Transactional(readOnly = true)
public class StateService {
    private final StateRepository stateRepository;
    private final StateMapper stateMapper;
    private static final Logger log = LoggerFactory.getLogger(StateService.class);
    private static final String CLASS_NAME = StateService.class.getSimpleName();

    public StateService(StateRepository stateRepository, StateMapper stateMapper) {
        this.stateRepository = stateRepository;
        this.stateMapper = stateMapper;
    }

    public List<StateResponseDto> findAll() {
        log.info("Fetching all states from the database.");

        Instant start = Instant.now();

        List<StateResponseDto> responseList = stateRepository.findAll()
            .stream().map(stateMapper::toResponse).toList();

        long timeTaken = calculateTimeTaken(start);
        logPerformanceTwoSeconds(CLASS_NAME, "findAll", timeTaken, responseList.size());
        return responseList;
    }

    public StateResponseDto findByName(String name) {
        log.info("Fetching state by name in the database.");

        Instant start = Instant.now();

        var state = stateRepository.findByNameIgnoreCase(name.trim()).orElseThrow(() -> new NotFoundException("State with name: " + name + " not found"));

        long timeTaken = calculateTimeTaken(start);
        logPerformanceTwoSeconds(CLASS_NAME, "findByName", timeTaken, 1);

        return stateMapper.toResponse(state);
    }


}
