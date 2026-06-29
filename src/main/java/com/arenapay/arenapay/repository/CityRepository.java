package com.arenapay.arenapay.repository;

import com.arenapay.arenapay.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface CityRepository extends JpaRepository<City, UUID> {
    @Query("SELECT COUNT(c) > 0 FROM City c WHERE function('unaccent', lower(c.name)) = function('unaccent', lower(:name)) AND c.state.id = :stateId")
    boolean existsByNameIgnoreCaseAndStateId(String name, UUID stateId);
}
