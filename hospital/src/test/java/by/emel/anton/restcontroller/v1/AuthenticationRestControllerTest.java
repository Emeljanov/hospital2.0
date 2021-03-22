package by.emel.anton.restcontroller.v1;

import by.emel.anton.entity.Role;
import by.emel.anton.repository.jpa.PatientCardJpaRepository;
import by.emel.anton.repository.jpa.TherapyJpaRepository;
import by.emel.anton.repository.jpa.UserJpaRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = {"classpath:before-each.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class AuthenticationRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private TherapyJpaRepository therapyJpaRepository;
    @Autowired
    private PatientCardJpaRepository patientCardJpaRepository;

    private final String REQUEST_BODY_USER_PATH = "src/test/resources/request-body-login-user.json";
    private final String REQUEST_BODY_WRONG_USER_PATH = "src/test/resources/request-body-login-wrong-user.json";
    private final String KEY_ID = "id";
    private final String KEY_USER = "user";
    private final String KEY_TOKEN = "token";
    private final String KEY_ROLE_STRING = "roleString";
    private final String KEY_FIRST_NAME = "firstName";
    private final String KEY_LAST_NAME = "lastName";
    private final String KEY_BIRTHDAY = "birthday";
    private final String KEY_ACTIVE = "active";
    private final LocalDate adminBirthday = LocalDate.of(1800, 1, 1);

    @Test
    void shouldAuthenticate() throws Exception {

        String userString = getJsonStringFromFile(REQUEST_BODY_USER_PATH);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/auth/login").content(userString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject userResponseJson = new JSONObject(mvcResult.getResponse().getContentAsString());
        JSONObject userDetailsResponseJson = userResponseJson.getJSONObject(KEY_USER);

        assertNotNull(userResponseJson.getString(KEY_TOKEN));
        assertEquals(userDetailsResponseJson.getInt(KEY_ID), 3);
        assertEquals(userDetailsResponseJson.getString(KEY_ROLE_STRING), Role.ADMIN.toString());
        assertEquals(userDetailsResponseJson.getString(KEY_FIRST_NAME), "Drakula");
        assertEquals(userDetailsResponseJson.getString(KEY_LAST_NAME), "Pensilvanskiy");
        assertEquals(userDetailsResponseJson.getString(KEY_BIRTHDAY), adminBirthday.toString());
        assertTrue(userDetailsResponseJson.getBoolean(KEY_ACTIVE));

    }

    @Test
    void shouldGetBadRequestStatusWhenRequestBodyNotExist() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/auth/login"))
                .andExpect(status().isBadRequest())
                .andReturn();

    }

    @Test
    void shouldGetUnauthorizedStatusWhenUserNotExist() throws Exception {

        String userString = getJsonStringFromFile(REQUEST_BODY_WRONG_USER_PATH);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/auth/login").content(userString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }


    @Test
    @WithUserDetails("doctor")
    void shouldLogout() throws Exception {

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/auth/logout"))
                .andExpect(status().isOk())
                .andReturn();

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void shouldGetForbiddenStatusWhenNoAuth() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/auth/logout"))
                .andExpect(status().isForbidden())
                .andReturn();

    }

    private String getJsonStringFromFile(String path) throws IOException {
        return Files.readString(Paths.get(path));
    }
}