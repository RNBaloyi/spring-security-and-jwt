package com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.repo;

import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
