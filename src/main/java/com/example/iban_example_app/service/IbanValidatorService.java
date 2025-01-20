package com.example.iban_example_app.service;

import com.example.iban_example_app.domain.IbanValidationResult;

public interface IbanValidatorService {
  IbanValidationResult validateIban(String iban);
}
