package com.example.c51diplompersonaltrainerrest.repository;

import com.example.c51diplompersonaltrainerrest.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);

}
