package com.nuclea.data.repository.address;

import com.nuclea.data.entity.address.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for City entity.
 */
@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    List<City> findByCountryIdAndIsActiveTrueOrderByName(Long countryId);

    List<City> findAllByIsActiveTrueOrderByName();
}
