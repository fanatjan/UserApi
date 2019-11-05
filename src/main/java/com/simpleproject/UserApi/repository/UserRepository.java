package com.simpleproject.UserApi.repository;

import com.simpleproject.UserApi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
