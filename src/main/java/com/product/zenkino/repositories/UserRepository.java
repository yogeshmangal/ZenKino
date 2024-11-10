package com.product.zenkino.repositories;

import com.product.zenkino.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
