package com.example.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private Long CustomerId;

    private String userName;
    private String panCardNumber;
    private String aadharCardNumber;
    private String bankAccountNumber;
    private String accountType;
    private String password;
    private double balance;
    private double loan;

    private String address;

}
