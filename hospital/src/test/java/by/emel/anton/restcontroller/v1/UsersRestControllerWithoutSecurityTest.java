package by.emel.anton.restcontroller.v1;

import by.emel.anton.entity.Role;
import by.emel.anton.repository.jpa.UserJpaRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = {"classpath:before-each.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class UsersRestControllerWithoutSecurityTest {

    private static final String ID = "id";
    private static final String LOGIN = "login";
    private static final String DOCTOR = "doctor";
    private static final String PATIENT = "patient";
    private static final String ADMIN = "admin";
    private static final String ROLE_STRING = "roleString";
    private static final String ROLE = "role";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String BIRTHDAY = "birthday";
    private static final String ACTIVE = "active";
    private static final String REQUEST_BODY_PATIENT_PATH = "src/test/resources/request-body-create-user.json";
    private static final LocalDate doctorBirthday = LocalDate.of(1900, 1, 1);
    private static final LocalDate patientBirthday = LocalDate.of(1998, 2, 3);
    private static final LocalDate adminBirthday = LocalDate.of(1800, 1, 1);

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void shouldFindAll() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/users"))
                .andExpect(status().isOk())
                .andReturn();

        JSONArray jsonArray = new JSONArray(mvcResult.getResponse().getContentAsString());
        assertEquals(jsonArray.length(), 6);

        JSONObject doctorJson = jsonArray.getJSONObject(0);
        assertEquals(doctorJson.getInt(ID), 1);
        assertEquals(doctorJson.get(LOGIN), DOCTOR);
        assertEquals(doctorJson.get(FIRST_NAME), "Ivan");
        assertEquals(doctorJson.get(LAST_NAME), "Ivanov");
        assertEquals(doctorJson.get(BIRTHDAY), doctorBirthday.toString());
        assertEquals(doctorJson.get(ROLE_STRING), Role.DOCTOR.toString());
        assertTrue(doctorJson.getBoolean(ACTIVE));

        JSONObject patientJson = jsonArray.getJSONObject(1);
        assertEquals(patientJson.getInt(ID), 2);
        assertEquals(patientJson.get(LOGIN), PATIENT);
        assertEquals(patientJson.get(FIRST_NAME), "Petr");
        assertEquals(patientJson.get(LAST_NAME), "Petrov");
        assertEquals(patientJson.get(BIRTHDAY), patientBirthday.toString());
        assertEquals(patientJson.get(ROLE_STRING), Role.PATIENT.toString());
        assertTrue(patientJson.getBoolean(ACTIVE));

        JSONObject adminJson = jsonArray.getJSONObject(2);
        assertEquals(adminJson.getInt(ID), 3);
        assertEquals(adminJson.get(LOGIN), ADMIN);
        assertEquals(adminJson.get(FIRST_NAME), "Drakula");
        assertEquals(adminJson.get(LAST_NAME), "Pensilvanskiy");
        assertEquals(adminJson.get(BIRTHDAY), adminBirthday.toString());
        assertEquals(adminJson.get(ROLE_STRING), Role.ADMIN.toString());
        assertTrue(adminJson.getBoolean(ACTIVE));

    }

    private String getJsonStringFromFile(String path) throws IOException {
        return Files.readString(Paths.get(path));
    }
}