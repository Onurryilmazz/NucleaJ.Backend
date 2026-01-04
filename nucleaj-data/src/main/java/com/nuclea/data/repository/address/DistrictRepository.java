package com.nuclea.data.repository.address;

import com.nuclea.data.entity.address.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for District entity.
 */
@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {

    List<District> findByCityIdAndIsActiveTrueOrderByName(Long cityId);

    List<District> findAllByIsActiveTrueOrderByName();
}
