package com.example.iban_example_app.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class LocalIbanValidatorServiceTest {

  @Autowired
  private LocalIbanValidatorService localIbanValidatorService;

  @Test
  public void shouldReturnValidIbanAndBankCode() {
    var result = localIbanValidatorService.validateIban("DE02120300000000202051");

    assertTrue(result.valid());
    assertEquals("DE02120300000000202051", result.iban());
    assertEquals("12030000", result.bankData().bankCode());
  }
}
