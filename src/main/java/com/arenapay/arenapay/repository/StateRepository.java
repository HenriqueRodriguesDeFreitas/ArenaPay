package com.arenapay.arenapay.repository;

import com.arenapay.arenapay.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface StateRepository extends JpaRepository<State, UUID> {
    @Query("SELECT s FROM State s WHERE function('unaccent', lower(s.name)) = function('unaccent', lower(:name)) ")
    Optional<State> findByNameIgnoreCase(@Param("name") String name);
}
