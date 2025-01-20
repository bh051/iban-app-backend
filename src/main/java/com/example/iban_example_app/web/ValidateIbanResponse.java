package com.example.iban_example_app.web;

public record ValidateIbanResponse(
    Boolean valid,
    String iban,
    BankResponse bank
) { }
