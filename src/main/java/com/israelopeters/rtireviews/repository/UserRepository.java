package com.israelopeters.rtireviews.repository;

import com.israelopeters.rtireviews.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
