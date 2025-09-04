/*package com.example.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Entity.Customer;
import com.example.Repository.CustomerRepository;


@Service
public class CustomerService {
	 @Autowired
	    private CustomerRepository customerRepository;

	    public Customer saveCustomer(Customer customer) {
	        return customerRepository.save(customer);
	    }

	    public Optional<Customer> getCustomerById(Long id) {
	        return customerRepository.findById(id);
	    }

}*/


package com.example.Service;

import com.example.Entity.Customer;
import com.example.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}

