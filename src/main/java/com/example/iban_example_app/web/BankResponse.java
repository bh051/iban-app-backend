package com.example.iban_example_app.web;

import com.example.iban_example_app.domain.Bank;

public record BankResponse(
    String bankCode,
    String name,
    String zip,
    String city,
    String bic
) {
  public static BankResponse from(Bank bank) {
    return new BankResponse(bank.bankCode(), bank.name(), bank.zip(), bank.city(), bank.bic());
  }

  public Bank toDomain() {
    return new Bank(bankCode, name, zip, city, bic);
  }
}
