package com.example.SpringBootDemo;


import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc ;

    @Test
    public void should_create_employee_when_post_given_a_valid_body() throws Exception {
        String requestBody= """
                {
                    "name": "Tom",
                    "age": 21,
                    "gender": "Male",
                    "salary": 18000.00
                }
                """;
        mockMvc.perform(post("/employees").contentType(APPLICATION_JSON)
                        .content(requestBody))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void should_return_employees_when_get_given_employee_id() throws Exception{
        String requestBody= """
                {
                    "name": "Tom",
                    "age": 21,
                    "gender": "Male",
                    "salary": 18000.00
                }
                """;
        mockMvc.perform(post("/employees").contentType(APPLICATION_JSON).content(requestBody));
        mockMvc.perform(get("/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Tom"))
                .andExpect(jsonPath("$.age").value(21))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.salary").value(18000.00));
    }

    @Test
    public void should_return_all_employees_when_get() throws Exception{
        mockMvc.perform(get("/employees/").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}
