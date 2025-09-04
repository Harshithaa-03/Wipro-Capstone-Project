package com.example.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.DTO.CustomerDto;

@FeignClient(name = "customer-service", url = "http://localhost:8070/api/customer")

public interface CustomerClient {

	@GetMapping("/{id}")
    CustomerDto getCustomerById(@PathVariable("id") Long id);
}
