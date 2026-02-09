package com.ecommerce.user.repositories;

import com.ecommerce.user.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
}
