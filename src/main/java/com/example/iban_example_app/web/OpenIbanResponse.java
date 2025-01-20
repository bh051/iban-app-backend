package com.example.iban_example_app.web;

public record OpenIbanResponse(
    Boolean valid,
    String iban,
    BankResponse bankData
) { }
