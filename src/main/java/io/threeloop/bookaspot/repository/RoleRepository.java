package io.threeloop.bookaspot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.threeloop.bookaspot.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {}
