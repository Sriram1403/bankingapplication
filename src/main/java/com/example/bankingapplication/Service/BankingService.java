package com.example.bankingapplication.Service;

import com.example.bankingapplication.Entity.User;
import com.example.bankingapplication.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BankingService {

    @Autowired
    private UserRepository userRepository;

    // Deposit Service
    public void deposit(String username, double amount) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            double currentBalance = user.getBalance();
            user.setBalance(currentBalance + amount);
            userRepository.save(user);
        }
    }

    // Withdrawal Service
    public void withdraw(String username, double amount) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            double currentBalance = user.getBalance();
            if (currentBalance >= amount) {
                user.setBalance(currentBalance - amount);
                userRepository.save(user);
            }
        }
    }

    // Transfer Service
    public void transfer(String senderUsername, String receiverUsername, double amount) {
        Optional<User> optionalSender = userRepository.findByUsername(senderUsername);
        Optional<User> optionalReceiver = userRepository.findByUsername(receiverUsername);
        if (optionalSender.isPresent() && optionalReceiver.isPresent()) {
            User sender = optionalSender.get();
            User receiver = optionalReceiver.get();
            double senderBalance = sender.getBalance();
            if (senderBalance >= amount) {
                sender.setBalance(senderBalance - amount);
                receiver.setBalance(receiver.getBalance() + amount);
                userRepository.save(sender);
                userRepository.save(receiver);
            }
        }
    }
}
