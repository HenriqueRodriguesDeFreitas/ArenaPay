package com.arenapay.arenapay.repository;

import com.arenapay.arenapay.model.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StateRepository extends JpaRepository<State, UUID> {

}
