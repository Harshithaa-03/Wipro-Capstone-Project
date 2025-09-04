package com.example.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Entity.Payment;
import com.example.Exception.InsufficientBalanceException;
import com.example.Service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    
    @PostMapping("/do")
    public ResponseEntity<Payment> makePayment(@Valid @RequestBody Payment payment) {
        try {
            Payment processedPayment = paymentService.processPayment(payment);
            return ResponseEntity.ok(processedPayment);
        } catch (InsufficientBalanceException e) {

        	return ResponseEntity.status(400).body(null);
        } catch (Exception e) {

        	return ResponseEntity.status(500).body(null);
        }
    }
}

