package com.nuclea.data.repository.address;

import com.nuclea.data.entity.address.Neighbourhood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Neighbourhood entity.
 */
@Repository
public interface NeighbourhoodRepository extends JpaRepository<Neighbourhood, Long> {

    List<Neighbourhood> findByDistrictIdAndIsActiveTrueOrderByName(Long districtId);

    List<Neighbourhood> findAllByIsActiveTrueOrderByName();
}
