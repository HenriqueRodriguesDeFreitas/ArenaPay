package com.arenapay.arenapay.repository;

import com.arenapay.arenapay.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CityRepository extends JpaRepository<City, UUID> {
}
