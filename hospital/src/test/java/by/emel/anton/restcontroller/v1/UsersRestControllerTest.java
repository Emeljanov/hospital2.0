package by.emel.anton.restcontroller.v1;

import by.emel.anton.entity.Role;
import by.emel.anton.entity.User;
import by.emel.anton.repository.jpa.UserJpaRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql(value = {"classpath:before-each.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class UsersRestControllerTest {

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
    @WithUserDetails("admin")
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

    @Test
    @WithUserDetails("patient")
    void shouldGetUnauthorizedStatusWhenNoAuthorizationInGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/users"))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    void shouldGetBadRequestStatusWhenNoAuthenticationInGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/users"))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @WithUserDetails("admin")
    void shouldFindUserById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/users/{id}", 1))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject userJson = new JSONObject(mvcResult.getResponse().getContentAsString());

        assertEquals(userJson.getInt(ID), 1);
        assertEquals(userJson.get(LOGIN), DOCTOR);
        assertEquals(userJson.get(FIRST_NAME), "Ivan");
        assertEquals(userJson.get(LAST_NAME), "Ivanov");
        assertEquals(userJson.get(BIRTHDAY), doctorBirthday.toString());
        assertEquals(userJson.get(ROLE_STRING), Role.DOCTOR.toString());
        assertTrue(userJson.getBoolean(ACTIVE));

    }

    @Test
    @WithUserDetails("doctor")
    void shouldGetBadRequestStatusWhenEntityNotFind() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/users/{id}", 0))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @WithUserDetails("patient")
    void shouldGetUnauthorizedStatusWhenNoAuthorizationInGetById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/users/{id}", 1))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    void shouldGetBadRequestStatusWhenNoAuthenticationInGetById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/users/{1}", 1))
                .andExpect(status().isForbidden())
                .andReturn();
    }


    @Test
    @WithUserDetails("admin")
    void shouldSaveUser() throws Exception {

        String userString = getJsonStringFromFile(REQUEST_BODY_PATIENT_PATH);
        JSONObject userJson = new JSONObject(userString);

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/api/v1/users").content(userString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        User userDB = userJpaRepository.findByLogin("create").get();

        assertEquals(userDB.getLogin(), userJson.getString(LOGIN));
        assertEquals(userDB.getLastName(), userJson.getString(LAST_NAME));
        assertEquals(userDB.getFirstName(), userJson.getString(FIRST_NAME));
        assertEquals(userDB.getBirthday(), LocalDate.parse(userJson.getString(BIRTHDAY)));
        assertEquals(userDB.getRole(), Role.valueOf(userJson.getString(ROLE)));
        assertTrue(passwordEncoder.matches("created_password", userDB.getPass()));

        JSONObject userResponse = new JSONObject(mvcResult.getResponse().getContentAsString());

        assertEquals(userResponse.getInt(ID), userDB.getId());
        assertEquals(userResponse.get(LOGIN), userDB.getLogin());
        assertEquals(userResponse.get(FIRST_NAME), userDB.getFirstName());
        assertEquals(userResponse.get(LAST_NAME), userDB.getLastName());
        assertEquals(userResponse.get(BIRTHDAY), userDB.getBirthday().toString());
        assertEquals(userResponse.get(ROLE_STRING), userDB.getRole().toString());
        assertTrue(userResponse.getBoolean(ACTIVE));

    }


    @Test
    @WithUserDetails("doctor")
    void shouldGetBadRequestStatusWhenNoRequestBodyInPost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/users"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @WithUserDetails("patient")
    void shouldGetUnauthorizedStatusWhenNoAuthorizationInPost() throws Exception {
        String userString = getJsonStringFromFile(REQUEST_BODY_PATIENT_PATH);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/users").content(userString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    void shouldGetBadRequestStatusWhenNoAuthenticationInPost() throws Exception {
        String userString = getJsonStringFromFile(REQUEST_BODY_PATIENT_PATH);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/users").content(userString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();
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

    @Test
    @WithUserDetails("admin")
    void shouldGetBadRequestStatusWhenNoAuthorizationInPut() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/v1/users/change/status/{userId}/{isActive}", 0, false))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @WithUserDetails("doctor")
    void shouldGetUnauthorizedStatusWhenNoAuthorizationInPut() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/v1/users/change/status/{userId}/{isActive}", 1, false))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    void shouldGetBadRequestStatusWhenNoAuthenticationInPut() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/v1/users/change/status/{userId}/{isActive}", 1, false))
                .andExpect(status().isForbidden())
                .andReturn();
    }


    private String getJsonStringFromFile(String path) throws IOException {
        return Files.readString(Paths.get(path));
    }
}