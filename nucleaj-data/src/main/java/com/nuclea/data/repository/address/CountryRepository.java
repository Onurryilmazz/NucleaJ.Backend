package com.nuclea.data.repository.address;

import com.nuclea.data.entity.address.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Country entity.
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    Optional<Country> findByCode(String code);

    List<Country> findAllByIsActiveTrueOrderByName();
}
