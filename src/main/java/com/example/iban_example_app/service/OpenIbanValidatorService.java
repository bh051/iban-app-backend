package com.example.iban_example_app.service;

import com.example.iban_example_app.domain.IbanValidationResult;
import com.example.iban_example_app.web.OpenIbanResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.Objects;

/**
 * Calls the external REST API https://openiban.com/validate to validate an IBAN and query bank data.
 */
@Primary
@Service
public class OpenIbanValidatorService implements IbanValidatorService {

  private final RestClient restClient = RestClient.builder().build();

  // Results will be cached in a redis database
  @Cacheable(cacheNames = {"ibanCache"})
  @Override
  public IbanValidationResult validateIban(String iban) {
    var openIbanResponse =
        Objects.requireNonNull(
            restClient
                .get()
                .uri("https://openiban.com/validate/{iban}?getBIC=true", Map.of("iban", iban))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(OpenIbanResponse.class)
                .getBody());

    return new IbanValidationResult(
        openIbanResponse.valid(), openIbanResponse.iban(), openIbanResponse.bankData().toDomain());
  }
}
