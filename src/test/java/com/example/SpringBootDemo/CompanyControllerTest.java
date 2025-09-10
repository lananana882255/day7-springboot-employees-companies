package com.example.SpringBootDemo;
import com.example.SpringBootDemo.Controller.CompanyController;
import org.junit.jupiter.api.BeforeEach;
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
    @Autowired
    private CompanyController companyController;
    @BeforeEach
    public void setUp(){
        companyController.clearCompanies();
    }
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

    @Test
    public void should_return_companies_when_get_given_page_and_size() throws Exception {
        String companyJsonA = """
                {
                    "name": "Spring1"
                }
                """;
        String companyJsonB = """
                {
                    "name": "Spring2"
                }
                """;
        String companyJsonC = """
                {
                    "name": "Spring3"
                }
                """;
        String companyJsonD = """
                {
                    "name": "Spring4"
                }
                """;
        String companyJsonE = """
                {
                    "name": "Spring5"
                }
                """;
        String companyJsonF = """
                {
                    "name": "Spring6"
                }
                """;
        mockMvc.perform(post("/companies").contentType(APPLICATION_JSON).content(companyJsonA));
        mockMvc.perform(post("/companies").contentType(APPLICATION_JSON).content(companyJsonB));
        mockMvc.perform(post("/companies").contentType(APPLICATION_JSON).content(companyJsonC));
        mockMvc.perform(post("/companies").contentType(APPLICATION_JSON).content(companyJsonD));
        mockMvc.perform(post("/companies").contentType(APPLICATION_JSON).content(companyJsonE));
        mockMvc.perform(post("/companies").contentType(APPLICATION_JSON).content(companyJsonF));
        mockMvc.perform(get("/companies?page=1&size=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(jsonPath("$[0].name").value("Spring1"))
                .andExpect(jsonPath("$[1].name").value("Spring2"))
                .andExpect(jsonPath("$[2].name").value("Spring3"))
                .andExpect(jsonPath("$[3].name").value("Spring4"))
                .andExpect(jsonPath("$[4].name").value("Spring5"));
    }

    @Test
    public void should_return_company_when_put_given_an_company_name() throws Exception {
        String companyJson = """
                {
                    "name": "Spring"
                }
                """;
       mockMvc.perform(post("/companies").contentType(APPLICATION_JSON).content(companyJson));

        String updateJson = """
                {
                    "name": "Spring1"
                }
                """;
        mockMvc.perform(put("/companies/1/name").contentType(APPLICATION_JSON).content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Spring1"));
    }

    @Test
    public void should_return_status204_when_delete_given_an_company_id() throws Exception {
        String companyJson = """
                {
                    "name": "Spring"
                }
                """;
        mockMvc.perform(post("/companies").contentType(APPLICATION_JSON).content(companyJson));
        mockMvc.perform(delete("/companies/1")).andExpect(status().isNoContent());
        mockMvc.perform(delete("/companies/1")).andExpect(status().isNotFound());
    }

}
