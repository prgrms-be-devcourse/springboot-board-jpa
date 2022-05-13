package com.example.springbootboardjpa.repository;

import com.example.springbootboardjpa.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
