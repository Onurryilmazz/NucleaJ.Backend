package com.nuclea.data.repository.user;

import com.nuclea.data.entity.user.User;
import com.nuclea.data.repository.base.SoftDeleteRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for User entity.
 */
@Repository
public interface UserRepository extends SoftDeleteRepository<User, Long> {

    Optional<User> findByEmailAndDeletedDateIsNull(String email);

    boolean existsByEmailAndDeletedDateIsNull(String email);
}
