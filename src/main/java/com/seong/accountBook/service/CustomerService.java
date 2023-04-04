package com.seong.accountBook.service;

import com.seong.accountBook.entity.Customer;
import com.seong.accountBook.entity.dto.CustomerDTO;
import com.seong.accountBook.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer signup(Customer customer) {
        return customerRepository.save(customer);
    }

    public Optional<Customer> login(String email, String password) {
        return customerRepository.findByEmailAndPassword(email, password);
    }

    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }
}
