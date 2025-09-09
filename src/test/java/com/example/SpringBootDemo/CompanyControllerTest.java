package com.example.SpringBootDemo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_create_company_when_post_given_a_valid_company() throws Exception {
        String companyJson = """
                {
                    "name": "Spring"
                }
                """;
        mockMvc.perform(post("/companies").contentType(APPLICATION_JSON)
                        .content(companyJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

}
