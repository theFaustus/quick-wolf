package com.quickwolf.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quickwolf.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByEmail(String email);
}
