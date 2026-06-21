package com.arenapay.arenapay.service;

import com.arenapay.arenapay.model.State;
import com.arenapay.arenapay.repository.StateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateService {
    private final StateRepository stateRepository;

    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    public List<State> findAll() {
        return stateRepository.findAll();
    }
}
