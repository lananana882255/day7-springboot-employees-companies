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


    @Test
    public void should_return_company_when_get_given_company_id() throws Exception {
        String companyJson = """
                {
                    "name": "Spring"
                }
                """;
        mockMvc.perform(post("/companies").contentType(APPLICATION_JSON).content(companyJson));
        mockMvc.perform(get("/companies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Spring"));
    }

    @Test
    public void should_return_all_companies_when_get() throws Exception {
        String companyJsonA = """
                {
                    "name": "Spring"
                }
                """;
        String companyJsonB = """
                {
                    "name": "Boot"
                }
                """;
        mockMvc.perform(post("/companies").contentType(APPLICATION_JSON).content(companyJsonA));
        mockMvc.perform(post("/companies").contentType(APPLICATION_JSON).content(companyJsonB));
        mockMvc.perform(get("/companies").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

}
