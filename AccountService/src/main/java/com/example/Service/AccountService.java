package com.example.Service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.Entity.Account;
import com.example.Feign.CustomerClient;
import com.example.Repository.AccountRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerClient customerClient;

    public AccountService(AccountRepository accountRepository, CustomerClient customerClient) {
        this.accountRepository = accountRepository;
        this.customerClient = customerClient;
    }

    public Account saveAccount(Account account) {
        
        try {
            customerClient.getCustomerById(account.getCustomerId());
        } catch (Exception e) {
            throw new RuntimeException("Customer not found with id: " + account.getCustomerId());
        }
        return accountRepository.save(account);
    }

    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    public Page<Account> getAccountsByAddress(String address, Pageable pageable) {
        return accountRepository.findByAddress(address, pageable);
    }

    public Page<Account> getAllAccounts(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }
}
