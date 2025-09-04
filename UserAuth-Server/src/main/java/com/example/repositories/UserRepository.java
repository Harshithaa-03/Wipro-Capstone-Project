package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.User;
import com.example.enums.UserRole;

import java.util.Optional;


@Repository
public interface UserRepository  extends JpaRepository<User, Long>
{

	 Optional<User> findFirstByEmail(String username);

	 Optional<User> findByUserRole(UserRole admin);

}
