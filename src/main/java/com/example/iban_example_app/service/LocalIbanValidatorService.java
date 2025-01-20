package com.example.iban_example_app.service;

import com.example.iban_example_app.domain.Bank;
import com.example.iban_example_app.domain.IbanValidationResult;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class LocalIbanValidatorService implements IbanValidatorService {

  @Cacheable(cacheNames = {"ibanCache"})
  @Override
  public IbanValidationResult validateIban(String iban) {
    if (iban.startsWith("DE")) {
      var bankCode = iban.substring(4, 12);
      var bank = switch (bankCode) {
        case "12030000" -> new Bank(bankCode, "Deutsche Kreditbank Berlin", "", "", "BYLADEM1001");
        case "10010010" -> new Bank(bankCode, "Postbank", "", "", "PBNKDEFF");
        case "20050550" -> new Bank(bankCode, "Hamburger Sparkasse", "", "", "HASPDEHH");
        default -> throw new IllegalArgumentException("iban.bankCode.unknown");
      };
      return new IbanValidationResult(
          true,
          iban,
          bank
      );
    }
    throw new IllegalArgumentException("iban.country.unknown");
  }
}
