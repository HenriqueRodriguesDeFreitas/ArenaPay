package com.arenapay.arenapay.service;

import com.arenapay.arenapay.dto.response.StateResponseDto;
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
public class StateService {
    private final StateRepository stateRepository;
    private final StateMapper stateMapper;
    private static final Logger log = LoggerFactory.getLogger(StateService.class);

    public StateService(StateRepository stateRepository, StateMapper stateMapper) {
        this.stateRepository = stateRepository;
        this.stateMapper = stateMapper;
    }

    @Transactional
    public List<StateResponseDto> findAll() {
        log.info("Fetching all states from the database.");

        Instant start = Instant.now();

        List<StateResponseDto> responseList = stateRepository.findAll()
                .stream().map(stateMapper::toResponse).toList();

        long timeTaken = Duration.between(start, Instant.now()).toMillis();
        if (timeTaken > 2000) {
            log.warn("PERFORMANCE WARNING: The listing took {}ms to respond!", timeTaken);
        } else {
            log.info("Search finished. Time taken: {}ms to find {} states in the database.", timeTaken, responseList.size());
        }
        return responseList;
    }
}
