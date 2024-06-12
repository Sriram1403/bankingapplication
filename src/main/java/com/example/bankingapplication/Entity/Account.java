package com.example.bankingapplication.Entity;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   long id;
    Double Balance;


}
