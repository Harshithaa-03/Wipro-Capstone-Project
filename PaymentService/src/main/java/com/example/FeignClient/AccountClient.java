package com.example.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.DTO.AccountDto;

@FeignClient(name = "account-service", url = "http://localhost:8071/api/accounts")
public interface AccountClient {
    
    @GetMapping("/{id}")
    AccountDto getAccountById(@PathVariable("id") Long id);

    @PutMapping("/{accountNumber}/withdraw")
    void withdraw(@PathVariable String accountNumber, @RequestParam double amount);

    @PutMapping("/{accountNumber}/deposit")
    void deposit(@PathVariable String accountNumber, @RequestParam double amount);
}

