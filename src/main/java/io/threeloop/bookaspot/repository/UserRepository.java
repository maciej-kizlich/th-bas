package io.threeloop.bookaspot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.threeloop.bookaspot.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
  User findOneByUsername(String name);
  User findOneByEmail(String email);
  User findOneByUsernameOrEmail(String username, String email);
  
}
