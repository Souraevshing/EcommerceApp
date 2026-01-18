package com.ecommerce.app.repository;

import com.ecommerce.app.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
}
