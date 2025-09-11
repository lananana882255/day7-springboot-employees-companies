package com.example.SpringBootDemo.Controller;


import com.example.SpringBootDemo.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeesController employeesController;

    @BeforeEach
    void setUp() {
        employeesController.clearEmployees();
    }

    private long createEmployee(String requestBody) throws Exception {
        ResultActions resultActions = mockMvc.perform(post("/employees").contentType(APPLICATION_JSON).content(requestBody));
        MvcResult mvcResult = resultActions.andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        return new ObjectMapper().readTree(response).get("id").asLong();
    }

    //TODO
    @Test
    public void should_not_create_employee_when_post_given_an_existed_employee() throws Exception {
        String employeeJson = """
                {
                    "name": "Tom",
                    "age": 21,
                    "gender": "Male",
                    "salary": 18000.00
                }
                """;
        mockMvc.perform(post("/employees").contentType(APPLICATION_JSON)
                        .content(employeeJson))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/employees").contentType(APPLICATION_JSON)
                        .content(employeeJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_not_create_employee_when_post_given_an_employee_with_age_over_30_and_salary_below_20000() throws Exception {
        String employeeJson = """
                {
                    "name": "Tom",
                    "age": 31,
                    "gender": "Male",
                    "salary": 18000.00
                }
                """;
        mockMvc.perform(post("/employees").contentType(APPLICATION_JSON)
                        .content(employeeJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_create_employee_when_post_given_a_valid_body() throws Exception {
        String employeeJson = """
                {
                    "name": "Tom",
                    "age": 21,
                    "gender": "Male",
                    "salary": 18000.00
                }
                """;
        long employeeId = createEmployee(employeeJson);
        mockMvc.perform(post("/employees").contentType(APPLICATION_JSON)
                        .content(employeeJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(employeeId + 1));
    }

    @Test
    public void should_return_404_when_get_given_invalid_employee_id() throws Exception {
        String employeeJson = """
                {
                    "name": "Tom",
                    "age": 21,
                    "gender": "Male",
                    "salary": 18000.00
                }
                """;
        long employeeId = createEmployee(employeeJson);
        mockMvc.perform(get("/employees/" + employeeId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_return_employees_when_get_given_employee_id() throws Exception {
        String employeeJson = """
                {
                    "name": "Tom",
                    "age": 21,
                    "gender": "Male",
                    "salary": 18000.00
                }
                """;
        long employeeId = createEmployee(employeeJson);
        mockMvc.perform(get("/employees/" + employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employeeId))
                .andExpect(jsonPath("$.name").value("Tom"))
                .andExpect(jsonPath("$.age").value(21))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.salary").value(18000.00));
    }

    @Test
    public void should_return_male_employees_when_get_given_male_gender() throws Exception {
        String employeeJsonA = """
                {
                    "name": "Tom",
                    "age": 21,
                    "gender": "Male",
                    "salary": 18000.00
                }
                """;
        String employeeJsonB = """
                {
                    "name": "Tina",
                    "age": 22,
                    "gender": "Female",
                    "salary": 28000.00
                }
                """;
        String employeeJsonC = """
                {
                    "name": "Tony",
                    "age": 41,
                    "gender": "Male",
                    "salary": 38000.00
                }
                """;
        mockMvc.perform(post("/employees").contentType(APPLICATION_JSON).content(employeeJsonA));
        mockMvc.perform(post("/employees").contentType(APPLICATION_JSON).content(employeeJsonB));
        mockMvc.perform(post("/employees").contentType(APPLICATION_JSON).content(employeeJsonC));
        mockMvc.perform(get("/employees?gender=Male"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].gender").value("Male"))
                .andExpect(jsonPath("$[0].name").value("Tom"))
                .andExpect(jsonPath("$[1].gender").value("Male"))
                .andExpect(jsonPath("$[1].name").value("Tony"));

    }

    @Test
    public void should_return_all_employees_when_get() throws Exception {
        String employeeJsonA = """
                {
                    "name": "Tom",
                    "age": 21,
                    "gender": "Male",
                    "salary": 18000.00
                }
                """;
        String employeeJsonB = """
                {
                    "name": "Tina",
                    "age": 22,
                    "gender": "Female",
                    "salary": 28000.00
                }
                """;
        mockMvc.perform(post("/employees").contentType(APPLICATION_JSON).content(employeeJsonA));
        mockMvc.perform(post("/employees").contentType(APPLICATION_JSON).content(employeeJsonB));
        mockMvc.perform(get("/employees").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void should_return_employee_when_put_given_an_employee_age_and_salry() throws Exception {
        String employeeJson = """
                {
                    "name": "Tom",
                    "age": 21,
                    "gender": "Male",
                    "salary": 18000.00
                }
                """;
        long employeeId = createEmployee(employeeJson);
        String updateJson = """
                {
                    "age": 25,
                    "salary": 20000.00
                }
                """;
        mockMvc.perform(put("/employees/" + employeeId).contentType(APPLICATION_JSON).content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age").value(25))
                .andExpect(jsonPath("$.salary").value(20000.00));
    }

    @Test
    public void should_return_404_when_put_given_an_invalid_employee_id_and_updateImformation() throws Exception {
        String updateJson = """
                {
                    "name": "Tony",
                    "age": 21,
                    "gender": "Male",
                    "salary": 18000.00
                }
                """;

        mockMvc.perform(put("/employees/9999" ).contentType(APPLICATION_JSON).content(updateJson))
                .andExpect(status().isNotFound());
    }


    @Test
    public void should_return_404_when_delete_given_an_invalid_employee_id() throws Exception {
        String employeeJson = """
                {
                    "name": "Tom",
                    "age": 21,
                    "gender": "Male",
                    "salary": 18000.00
                }
                """;
        long employeeId = createEmployee(employeeJson);
        mockMvc.perform(delete("/employees/" + employeeId + 1)).andExpect(status().isNotFound());
    }

    @Test
    public void should_return_204_when_delete_given_an_employee_id() throws Exception {
        String employeeJson = """
                {
                    "name": "Tom",
                    "age": 21,
                    "gender": "Male",
                    "salary": 18000.00
                }
                """;
        long employeeId = createEmployee(employeeJson);
        mockMvc.perform(delete("/employees/" + employeeId)).andExpect(status().isNoContent());
    }


    @Test
    public void should_return_400_when_delete_given_already_deleted_employee_id() throws Exception {
        String employeeJson = """
                {
                    "name": "Tom",
                    "age": 21,
                    "gender": "Male",
                    "salary": 18000.00
                }
                """;
        long employeeId = createEmployee(employeeJson);
        mockMvc.perform(delete("/employees/" + employeeId)).andExpect(status().isNoContent());
        mockMvc.perform(delete("/employees/" + employeeId)).andExpect(status().isBadRequest());
    }

    @Test
    public void should_return_employees_when_get_given_page_and_size() throws Exception {
        String employeeJsonA = """
                {
                    "name": "Tom1",
                    "age": 21,
                    "gender": "Male",
                    "salary": 18000.00
                }
                """;
        String employeeJsonB = """
                {
                    "name": "Tom2",
                    "age": 21,
                    "gender": "Male",
                    "salary": 18000.00
                }
                """;
        String employeeJsonC = """
                {
                    "name": "Tom3",
                    "age": 21,
                    "gender": "Male",
                    "salary": 18000.00
                }
                """;
        String employeeJsonD = """
                {
                    "name": "Tom4",
                    "age": 21,
                    "gender": "Male",
                    "salary": 18000.00
                }
                """;
        String employeeJsonE = """
                {
                    "name": "Tom5",
                    "age": 21,
                    "gender": "Male",
                    "salary": 18000.00
                }
                """;
        String employeeJsonF = """
                {
                    "name": "Tom6",
                    "age": 21,
                    "gender": "Male",
                    "salary": 18000.00
                }
                """;
        mockMvc.perform(post("/employees").contentType(APPLICATION_JSON).content(employeeJsonA));
        mockMvc.perform(post("/employees").contentType(APPLICATION_JSON).content(employeeJsonB));
        mockMvc.perform(post("/employees").contentType(APPLICATION_JSON).content(employeeJsonC));
        mockMvc.perform(post("/employees").contentType(APPLICATION_JSON).content(employeeJsonD));
        mockMvc.perform(post("/employees").contentType(APPLICATION_JSON).content(employeeJsonE));
        mockMvc.perform(post("/employees").contentType(APPLICATION_JSON).content(employeeJsonF));
        mockMvc.perform(get("/employees?page=1&size=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(jsonPath("$[0].name").value("Tom1"))
                .andExpect(jsonPath("$[1].name").value("Tom2"))
                .andExpect(jsonPath("$[2].name").value("Tom3"))
                .andExpect(jsonPath("$[3].name").value("Tom4"))
                .andExpect(jsonPath("$[4].name").value("Tom5"));
    }
}