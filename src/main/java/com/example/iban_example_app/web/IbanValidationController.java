package com.example.iban_example_app.web;

import com.example.iban_example_app.service.IbanValidatorService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
public class IbanValidationController {

  private IbanValidatorService ibanValidatorService;

  public IbanValidationController(IbanValidatorService ibanValidatorService) {
    this.ibanValidatorService = ibanValidatorService;
  }

  @GetMapping("/iban/info")
  public String ibanInfo() {
    return "This is an IBAN Validator demo application";
  }

  @PostMapping(
      value = "/iban/validate",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ValidateIbanResponse> validateIban(
      @Valid @RequestBody ValidateIbanRequest validateIbanRequest) {
    var ibanValidationResult = ibanValidatorService.validateIban(validateIbanRequest.iban());
    return ResponseEntity.ok(
        new ValidateIbanResponse(
            ibanValidationResult.valid(),
            ibanValidationResult.iban(),
            BankResponse.from(ibanValidationResult.bankData())));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
      MethodArgumentNotValidException e) {
    if (Objects.requireNonNull(e.getFieldError()).getField().equals("iban")) {
      return ResponseEntity.badRequest()
          .body(new ErrorResponse("iban.format.invalid", "Invalid IBAN format."));
    }
    return ResponseEntity.badRequest()
        .body(new ErrorResponse(e.getFieldError().getCode(), e.getMessage()));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Void> handleIllegalArgumentException(IllegalArgumentException e) {
    return ResponseEntity.badRequest().build();
  }
}
