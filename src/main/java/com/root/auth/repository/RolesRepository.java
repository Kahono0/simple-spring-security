package com.root.auth.repository;

import com.root.auth.models.Roles;
import com.root.auth.models.UserRoles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {
    Roles findByName(UserRoles name);
}
