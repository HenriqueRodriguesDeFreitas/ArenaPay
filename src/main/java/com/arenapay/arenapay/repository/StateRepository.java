package com.arenapay.arenapay.repository;

import com.arenapay.arenapay.model.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StateRepository extends JpaRepository<State, UUID> {
    Optional<State> findByNameIgnoreCase(String name);
}
