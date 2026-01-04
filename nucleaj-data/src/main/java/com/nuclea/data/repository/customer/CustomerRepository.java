package com.nuclea.data.repository.customer;

import com.nuclea.data.entity.customer.Customer;
import com.nuclea.data.repository.base.SoftDeleteRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for Customer entity.
 */
@Repository
public interface CustomerRepository extends SoftDeleteRepository<Customer, Long> {

    Optional<Customer> findByEmailAndDeletedDateIsNull(String email);

    Optional<Customer> findByGoogleIdAndDeletedDateIsNull(String googleId);

    Optional<Customer> findByAppleIdAndDeletedDateIsNull(String appleId);

    boolean existsByEmailAndDeletedDateIsNull(String email);

    boolean existsByGoogleIdAndDeletedDateIsNull(String googleId);

    boolean existsByAppleIdAndDeletedDateIsNull(String appleId);
}
