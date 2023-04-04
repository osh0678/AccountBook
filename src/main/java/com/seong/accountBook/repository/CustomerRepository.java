package com.seong.accountBook.repository;

import com.seong.accountBook.entity.Customer;
import com.seong.accountBook.entity.dto.CustomerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByEmailAndPassword(String email, String password);
    boolean existsByEmail(String email);
}
