package by.emel.anton.restcontroller.v1;

import by.emel.anton.entity.Role;
import by.emel.anton.repository.jpa.UserJpaRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql(value = {"classpath:before-each.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class UsersRestControllerTest {

    //  {"id":1,"login":"doctor","roleString":"DOCTOR","firstName":"Ivan","lastName":"Ivanov","birthday":"1900-01-01","active":true}

    private static final String KEY_AUTHORIZATION = "Authorization";
    private static final String ID = "id";
    private static final String LOGIN = "login";
    private static final String DOCTOR = "doctor";
    private static final String PATIENT = "patient";
    private static final String ADMIN = "admin";
    private static final String ROLE_STRING = "roleString";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String BIRTHDAY = "birthday";
    private static final String ACTIVE = "active";

    private static final LocalDate doctorBirthday = LocalDate.of(1900, 1, 1);
    private static final LocalDate patientBirthday = LocalDate.of(1998, 2, 3);
    private static final LocalDate adminBirthday = LocalDate.of(1800, 1, 1);


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserJpaRepository userJpaRepository;

//    private User admin;

    @Test
    @WithUserDetails("admin")
    void shouldFindAll() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/users"))
                .andExpect(status().isOk())
                .andReturn();

        JSONArray jsonArray = new JSONArray(mvcResult.getResponse().getContentAsString());
        assertEquals(jsonArray.length(), 4);

        JSONObject jsonDoctor = jsonArray.getJSONObject(0);
        assertEquals(jsonDoctor.getInt(ID), 1);
        assertEquals(jsonDoctor.get(LOGIN), DOCTOR);
        assertEquals(jsonDoctor.get(FIRST_NAME), "Ivan");
        assertEquals(jsonDoctor.get(LAST_NAME), "Ivanov");
        assertEquals(jsonDoctor.get(BIRTHDAY), doctorBirthday.toString());
        assertEquals(jsonDoctor.get(ROLE_STRING), Role.DOCTOR.toString());
        assertTrue(jsonDoctor.getBoolean(ACTIVE));

        JSONObject jsonPatient = jsonArray.getJSONObject(1);
        assertEquals(jsonPatient.getInt(ID), 2);
        assertEquals(jsonPatient.get(LOGIN), PATIENT);
        assertEquals(jsonPatient.get(FIRST_NAME), "Petr");
        assertEquals(jsonPatient.get(LAST_NAME), "Petrov");
        assertEquals(jsonPatient.get(BIRTHDAY), patientBirthday.toString());
        assertEquals(jsonPatient.get(ROLE_STRING), Role.PATIENT.toString());
        assertTrue(jsonPatient.getBoolean(ACTIVE));

        JSONObject jsonAdmin = jsonArray.getJSONObject(2);
        assertEquals(jsonAdmin.getInt(ID), 3);
        assertEquals(jsonAdmin.get(LOGIN), ADMIN);
        assertEquals(jsonAdmin.get(FIRST_NAME), "Drakula");
        assertEquals(jsonAdmin.get(LAST_NAME), "Pensilvanskiy");
        assertEquals(jsonAdmin.get(BIRTHDAY), adminBirthday.toString());
        assertEquals(jsonAdmin.get(ROLE_STRING), Role.ADMIN.toString());
        assertTrue(jsonAdmin.getBoolean(ACTIVE));

    }

    @Test
    @WithUserDetails("admin")
    void shouldFindUserById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/users/{id}", 1))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject jsonUser = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertEquals(jsonUser.getInt(ID), 1);
        assertEquals(jsonUser.get(LOGIN), DOCTOR);
        assertEquals(jsonUser.get(FIRST_NAME), "Ivan");
        assertEquals(jsonUser.get(LAST_NAME), "Ivanov");
        assertEquals(jsonUser.get(BIRTHDAY), doctorBirthday.toString());
        assertEquals(jsonUser.get(ROLE_STRING), Role.DOCTOR.toString());
        assertTrue(jsonUser.getBoolean(ACTIVE));

    }

    @Test
    void saveUser() {
    }

   /* @Test
    @WithUserDetails("admin")
    void deleteUserById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/users/{id}", 1, false))
                .andExpect(status().isOk())
                .andReturn();


        assertFalse(userJpaRepository.existsById(1));

    }*/

    @Test
    @WithUserDetails("admin")
    void shouldChangeUserStatus() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .put("/api/v1/users/change/status/{userId}/{isActive}", 1, false))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject jsonUser = new JSONObject(mvcResult.getResponse().getContentAsString());

        assertEquals(jsonUser.getInt(ID), 1);
        assertEquals(jsonUser.get(LOGIN), "doctor");
        assertEquals(jsonUser.get(FIRST_NAME), "Ivan");
        assertEquals(jsonUser.get(LAST_NAME), "Ivanov");
        assertEquals(jsonUser.get(BIRTHDAY), doctorBirthday.toString());
        assertEquals(jsonUser.get(ROLE_STRING), Role.DOCTOR.toString());
        assertFalse(jsonUser.getBoolean(ACTIVE));
    }
}