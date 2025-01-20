package com.example.iban_example_app.web;

import com.example.iban_example_app.domain.Bank;
import com.example.iban_example_app.domain.IbanValidationResult;
import com.example.iban_example_app.service.IbanValidatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(IbanValidationController.class)
public class IbanControllerIT {

  @MockitoBean
  private IbanValidatorService ibanValidatorService;
  @Autowired
  private WebApplicationContext webApplicationContext;
  private MockMvc mockMvc;

  @BeforeEach
  void beforeEach() {
    Mockito.when(ibanValidatorService.validateIban(anyString())).thenReturn(new IbanValidationResult(
        true,
        "DE02120300000000202051",
        new Bank(
            "20300000",
            "Testbank",
            "12345",
            "Stadt",
            "TEST123"
        )
    ));
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void givenCorrectIban_thenResponseOk() throws Exception {
    mockMvc.perform(
            post("/iban/validate")
                .content("{\"iban\":\"DE02120300000000202051\"}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
        .andExpect(jsonPath("$.valid").value(true))
        .andExpect(jsonPath("$.iban").value("DE02120300000000202051"))
        .andExpect(jsonPath("$.bank.bankCode").value("20300000"));
  }
}
