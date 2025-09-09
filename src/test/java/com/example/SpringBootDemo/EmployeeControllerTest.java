package com.example.SpringBootDemo;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc ;

    @Test
    public void should_create_employee_when_post_given_a_valid_body() throws Exception {

        String requestBody= """
                {
                    "name": "飞子欣",
                    "age": 21,
                    "gender": "男",
                    "salary": 38000
                }
                """;
        mockMvc.perform(post("/employees").contentType(APPLICATION_JSON)
                        .content(requestBody))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.id").value(1));
    }
}
