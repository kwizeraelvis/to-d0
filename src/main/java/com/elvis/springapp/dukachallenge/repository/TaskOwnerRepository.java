package com.elvis.springapp.dukachallenge.repository;

import com.elvis.springapp.dukachallenge.domain.TaskOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TaskOwnerRepository extends JpaRepository<TaskOwner, UUID> {
    Optional<TaskOwner> findDistinctByEmail(String email);
    Optional<TaskOwner> findDistinctByUsername(String username);
}
