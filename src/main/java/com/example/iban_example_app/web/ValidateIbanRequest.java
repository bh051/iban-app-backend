package com.example.iban_example_app.web;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record ValidateIbanRequest (
    @Pattern(regexp = "^[A-Z]{2}\\d{14,34}$")
    @NotEmpty
    String iban
) { }
