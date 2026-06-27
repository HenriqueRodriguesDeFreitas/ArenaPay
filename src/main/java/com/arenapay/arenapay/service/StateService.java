package com.arenapay.arenapay.service;

import com.arenapay.arenapay.dto.response.StateResponseDto;
import com.arenapay.arenapay.exception.custom.NotFoundException;
import com.arenapay.arenapay.mapper.StateMapper;
import com.arenapay.arenapay.repository.StateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class StateService {
    private final StateRepository stateRepository;
    private final StateMapper stateMapper;
    private static final Logger log = LoggerFactory.getLogger(StateService.class);

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
        logPerformanceTwoSeconds("findAll", timeTaken, responseList.size());
        return responseList;
    }

    public StateResponseDto findByName(String name) {
        log.info("Fetching state by name in the database.");

        Instant start = Instant.now();

        var state = stateRepository.findByNameIgnoreCase(name.trim()).orElseThrow(() -> new NotFoundException("State with name: " + name + " not found"));

        long timeTaken = calculateTimeTaken(start);
        logPerformanceTwoSeconds("findByName", timeTaken, 1);

        return stateMapper.toResponse(state);
    }

    private long calculateTimeTaken(Instant start) {
        return Duration.between(start, Instant.now()).toMillis();
    }

    private void logPerformanceTwoSeconds(String methodName, long timeTaken, int resultCount) {
        if (timeTaken > 2000) {
            log.warn("PERFORMANCE WARNING: Method '{}' took {}ms to respond!", methodName, timeTaken);
        } else {
            log.info("Method '{}' finished. Time taken: {}ms. Records found: {}", methodName, timeTaken, resultCount);
        }
    }
}
