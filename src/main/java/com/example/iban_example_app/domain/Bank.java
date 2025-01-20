package com.example.iban_example_app.domain;

public record Bank(
    String bankCode,
    String name,
    String zip,
    String city,
    String bic
) { }
