package com.quickwolf.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quickwolf.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
