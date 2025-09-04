package com.example.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	
}
