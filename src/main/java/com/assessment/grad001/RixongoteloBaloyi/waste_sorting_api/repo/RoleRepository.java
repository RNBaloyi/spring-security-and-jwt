package com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.repo;

import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.model.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(String roleAdmin);
}
