/*package com.example.Service;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import com.example.Entity.Payment;
import com.example.Kafka.NotificationEvent;
import com.example.Kafka.NotificationProducer;
import com.example.Repository.PaymentRepository;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final NotificationProducer notificationProducer;

    public PaymentService(PaymentRepository paymentRepository, NotificationProducer notificationProducer) {
        this.paymentRepository = paymentRepository;
        this.notificationProducer = notificationProducer;
    }

    public Payment processPayment(Payment payment) {
        try {
            payment.setTimestamp(LocalDateTime.now());
            payment.setPaymentDone(true);
            Payment saved = paymentRepository.save(payment);

            NotificationEvent successEvent = NotificationEvent.builder()
                    .email(payment.getSenderName() + "@bank.com") // placeholder email
                    .message("₹" + payment.getAmount() + " successfully sent to " + payment.getReceiverName())
                    .build();
            notificationProducer.sendNotification(successEvent);
            return saved;

        } catch (Exception e) {
            NotificationEvent failureEvent = NotificationEvent.builder()
                    .email(payment.getSenderName() + "@bank.com")
                    .message("Payment failed: " + e.getMessage())
                    .build();

            notificationProducer.sendNotification(failureEvent);
            throw new RuntimeException("Transaction failed", e);
        }
    }
}*/

package com.example.Service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.DTO.AccountDto;
import com.example.Entity.Payment;
import com.example.Enum.PaymentStatus;
import com.example.Exception.InsufficientBalanceException;
import com.example.FeignClient.AccountClient;
import com.example.Kafka.NotificationEvent;
import com.example.Kafka.NotificationProducer;
import com.example.Repository.PaymentRepository;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final NotificationProducer notificationProducer;
    private final AccountClient accountClient;

    public PaymentService(PaymentRepository paymentRepository,
                          NotificationProducer notificationProducer,
                          AccountClient accountClient) {
        this.paymentRepository = paymentRepository;
        this.notificationProducer = notificationProducer;
        this.accountClient = accountClient;
    }

    public Payment processPayment(Payment payment) {
        try {
            // Fetch sender and receiver account details
            AccountDto senderAccount = accountClient.getAccountById(payment.getSenderAccountId());
            AccountDto receiverAccount = accountClient.getAccountById(payment.getReceiverAccountId());

            // Check sender's balance
            if (senderAccount.getBalance() < payment.getAmount()) {
                throw new InsufficientBalanceException("Insufficient balance for account: " + senderAccount.getId());
            }

            // Deduct from sender
            accountClient.withdraw(senderAccount.getBankAccountNumber(), payment.getAmount());

            // Add to receiver
            accountClient.deposit(receiverAccount.getBankAccountNumber(), payment.getAmount());

            // Set payment details
            payment.setTimestamp(LocalDateTime.now());
            payment.setStatus(PaymentStatus.COMPLETED);

            // Save payment record
            Payment savedPayment = paymentRepository.save(payment);

            // Send Kafka notification for success
            NotificationEvent successEvent = NotificationEvent.builder()
                    .email(payment.getSenderName() + "@bank.com")
                    .message("₹" + payment.getAmount() + " sent successfully to " + payment.getReceiverName())
                    .build();

            notificationProducer.sendNotification(successEvent);

            return savedPayment;

        } catch (InsufficientBalanceException e) {
            // Send Kafka notification for insufficient balance
            NotificationEvent failureEvent = NotificationEvent.builder()
                    .email(payment.getSenderName() + "@bank.com")
                    .message("Payment failed: " + e.getMessage())
                    .build();

            notificationProducer.sendNotification(failureEvent);
            throw e;

        } catch (Exception e) {
            // Send Kafka notification for generic failure
            NotificationEvent failureEvent = NotificationEvent.builder()
                    .email(payment.getSenderName() + "@bank.com")
                    .message("Payment failed due to internal error")
                    .build();

            notificationProducer.sendNotification(failureEvent);
            throw new RuntimeException("Transaction failed", e);
        }
    }
}

