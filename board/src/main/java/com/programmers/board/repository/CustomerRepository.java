package com.programmers.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.board.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
