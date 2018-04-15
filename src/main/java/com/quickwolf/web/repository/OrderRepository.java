package com.quickwolf.web.repository;

import com.quickwolf.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Faust on 4/15/2018.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
}
