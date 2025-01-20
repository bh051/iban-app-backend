package com.example.iban_example_app.domain;

public record IbanValidationResult(
    Boolean valid,
    String iban,
    Bank bankData
) { }
