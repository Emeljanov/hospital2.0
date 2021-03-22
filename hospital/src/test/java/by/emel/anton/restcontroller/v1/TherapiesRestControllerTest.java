package by.emel.anton.restcontroller.v1;

import by.emel.anton.entity.Therapy;
import by.emel.anton.repository.jpa.TherapyJpaRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = {"classpath:before-each.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class TherapiesRestControllerTest {

    private final static String KEY_ID = "id";
    private final static String KEY_DESCRIPTION = "description";
    private final static String KEY_START_DATE = "startDate";
    private final static String KEY_END_DATE = "endDate";
    private final static String KEY_PATIENT_ID = "patientId";
    private final static String KEY_CARD_ID = "cardId";
    private final static String KEY_DOCTOR_ID = "doctorId";
    private final static String REQUEST_BODY_THERAPY_PATH = "src/test/resources/request-body-create-therapy.json";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TherapyJpaRepository therapyJpaRepository;

    private LocalDate startDate = LocalDate.of(2021, 1, 1);
    private LocalDate endDate = LocalDate.of(2025, 1, 1);

    @Test
    @WithUserDetails("doctor")
    void shouldFindAll() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/therapies"))
                .andExpect(status().isOk())
                .andReturn();

        JSONArray jsonArray = new JSONArray(mvcResult.getResponse().getContentAsString());
        assertEquals(jsonArray.length(), 1);

        JSONObject therapyJson = jsonArray.getJSONObject(0);
        assertEquals(therapyJson.getInt(KEY_ID), 1);
        assertEquals(therapyJson.get(KEY_DESCRIPTION), "Some description");
        assertEquals(therapyJson.get(KEY_START_DATE), startDate.toString());
        assertEquals(therapyJson.get(KEY_END_DATE), endDate.toString());
        assertEquals(therapyJson.get(KEY_CARD_ID), 1);
        assertEquals(therapyJson.get(KEY_DOCTOR_ID), 1);

    }

    @Test
    @WithUserDetails("patient")
    void shouldGetUnauthorizedStatusWhenNoAuthorizationInGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/therapies"))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    void shouldGetBadRequestStatusWhenNoAuthenticationInGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/therapies"))
                .andExpect(status().isForbidden())
                .andReturn();
    }


    @Test
    @WithUserDetails("doctor")
    void shouldFindTherapyById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/therapies/{id}", 1))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject therapyJson = new JSONObject(mvcResult.getResponse().getContentAsString());

        assertEquals(therapyJson.getInt(KEY_ID), 1);
        assertEquals(therapyJson.get(KEY_DESCRIPTION), "Some description");
        assertEquals(therapyJson.get(KEY_START_DATE), startDate.toString());
        assertEquals(therapyJson.get(KEY_END_DATE), endDate.toString());
        assertEquals(therapyJson.get(KEY_CARD_ID), 1);
        assertEquals(therapyJson.get(KEY_DOCTOR_ID), 1);
    }

    @Test
    @WithUserDetails("doctor")
    void shouldGetBadRequestStatusWhenNoAuthorizationInGetById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/therapies/{id}", 0))
                .andExpect(status().isBadRequest())
                .andReturn();
    }


    @Test
    @WithUserDetails("patient")
    void shouldGetUnauthorizedStatusWhenNoAuthorizationInGetById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/therapies/{id}", 1))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    void shouldGetForbiddenStatusWhenNoAuthenticationInGetById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/therapies/{id}", 1))
                .andExpect(status().isForbidden())
                .andReturn();
    }


    @Test
    @WithUserDetails("doctor")
    void ShouldSaveTherapy() throws Exception {

        String therapyString = getJsonStringFromFile(REQUEST_BODY_THERAPY_PATH);
        JSONObject therapyJson = new JSONObject(therapyString);

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/api/v1/therapies").content(therapyString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Therapy therapyDB = therapyJpaRepository.findById(2).get();

        assertEquals(therapyDB.getDescription(), therapyJson.getString(KEY_DESCRIPTION));
        assertEquals(therapyDB.getStartDate().toString(), therapyJson.getString(KEY_START_DATE));
        assertEquals(therapyDB.getEndDate().toString(), therapyJson.getString(KEY_END_DATE));
        assertEquals(therapyDB.getCard().getPatient().getId(), therapyJson.getInt(KEY_PATIENT_ID));
        assertEquals(therapyDB.getDoctor().getId(), 1);

        JSONObject therapyResponse = new JSONObject(mvcResult.getResponse().getContentAsString());

        assertEquals(therapyResponse.getInt(KEY_ID), therapyDB.getId());
        assertEquals(therapyResponse.getString(KEY_DESCRIPTION), therapyDB.getDescription());
        assertEquals(therapyResponse.getString(KEY_START_DATE), therapyDB.getStartDate().toString());
        assertEquals(therapyResponse.getString(KEY_END_DATE), therapyDB.getEndDate().toString());
        assertEquals(therapyResponse.getInt(KEY_CARD_ID), therapyDB.getCard().getId());

    }

    @Test
    @WithUserDetails("doctor")
    void shouldGetBadRequestStatusWhenNoRequestBodyInPost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/therapies"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @WithUserDetails("patient")
    void shouldGetUnauthorizedStatusWhenNoAuthorizationInPost() throws Exception {
        String userString = getJsonStringFromFile(REQUEST_BODY_THERAPY_PATH);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/therapies").content(userString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    void shouldGetBadRequestStatusWhenNoAuthenticationInPost() throws Exception {
        String userString = getJsonStringFromFile(REQUEST_BODY_THERAPY_PATH);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/therapies").content(userString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();
    }


    private String getJsonStringFromFile(String path) throws IOException {
        return Files.readString(Paths.get(path));
    }
}