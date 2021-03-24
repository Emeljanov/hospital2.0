package by.emel.anton.restcontroller.v1;

import by.emel.anton.entity.PatientCard;
import by.emel.anton.repository.jpa.PatientCardJpaRepository;
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

@SpringBootTest(properties = {"security.enable=true"})
@AutoConfigureMockMvc
@Sql(value = {"classpath:before-each.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CardsRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PatientCardJpaRepository patientCardJpaRepository;

    private final String REQUEST_BODY_CARD_PATH = "src/test/resources/request-body-create-card.json";
    private final String REQUEST_BODY_EXIST_CARD_PATH = "src/test/resources/request-wrong-body-create-card.json";

    private final String KEY_ID = "id";
    private final String KEY_DESCRIPTION = "description";
    private final String KEY_START_DATE = "startDate";
    private final String KEY_END_DATE = "endDate";
    private final String KEY_PATIENT_ID = "patientId";
    private final String KEY_CARD_ID = "cardId";
    private final String KEY_DOCTOR_ID = "doctorId";
    private LocalDate startDate = LocalDate.of(2021, 1, 1);
    private LocalDate endDate = LocalDate.of(2025, 1, 1);

    @Test
    @WithUserDetails("doctor")
    void shouldFindAllCards() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/cards"))
                .andExpect(status().isOk())
                .andReturn();

        JSONArray jsonArray = new JSONArray(mvcResult.getResponse().getContentAsString());
        assertEquals(jsonArray.length(), 2);

        JSONObject cardOne = jsonArray.getJSONObject(0);

        assertEquals(cardOne.getInt(KEY_ID), 1);
        assertEquals(cardOne.getInt(KEY_PATIENT_ID), 2);

        JSONArray jsonArrayFromCardOne = new JSONArray(cardOne.getString("therapyDTOList"));
        assertEquals(jsonArrayFromCardOne.length(), 1);

        JSONObject therapyJson = jsonArrayFromCardOne.getJSONObject(0);
        assertEquals(therapyJson.getInt(KEY_ID), 1);
        assertEquals(therapyJson.get(KEY_DESCRIPTION), "Some description");
        assertEquals(therapyJson.get(KEY_START_DATE), startDate.toString());
        assertEquals(therapyJson.get(KEY_END_DATE), endDate.toString());
        assertEquals(therapyJson.get(KEY_CARD_ID), 1);
        assertEquals(therapyJson.get(KEY_DOCTOR_ID), 1);

        JSONObject cardTwo = jsonArray.getJSONObject(1);
        JSONArray jsonArrayFromCardTwo = new JSONArray(cardTwo.getString("therapyDTOList"));
        assertEquals(jsonArrayFromCardTwo.length(), 0);

    }

    @Test
    @WithUserDetails("patient")
    void shouldGetUnauthorizedStatusWhenNoAuthorizationInGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/cards"))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    void shouldGetBadRequestStatusWhenNoAuthenticationInGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/cards"))
                .andExpect(status().isForbidden())
                .andReturn();
    }


    @Test
    @WithUserDetails("doctor")
    void findCardById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/cards/{id}", 1))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject cardOne = new JSONObject(mvcResult.getResponse().getContentAsString());

        assertEquals(cardOne.getInt(KEY_ID), 1);
        assertEquals(cardOne.getInt(KEY_PATIENT_ID), 2);

        JSONArray jsonArrayFromCardOne = new JSONArray(cardOne.getString("therapyDTOList"));
        assertEquals(jsonArrayFromCardOne.length(), 1);

        JSONObject therapyJson = jsonArrayFromCardOne.getJSONObject(0);
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
                .get("/api/v1/cards/{id}", 0))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @WithUserDetails("patient")
    void shouldGetUnauthorizedStatusWhenNoAuthorizationInGetById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/cards/{id}", 1))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    void shouldGetBadRequestStatusWhenNoAuthenticationInGetById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/cards/{id}", 1))
                .andExpect(status().isForbidden())
                .andReturn();
    }


    @Test
    @WithUserDetails("doctor")
    void findCardByPatientId() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/cards/patients/{id}", 2))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject cardOne = new JSONObject(mvcResult.getResponse().getContentAsString());

        assertEquals(cardOne.getInt(KEY_ID), 1);
        assertEquals(cardOne.getInt(KEY_PATIENT_ID), 2);

        JSONArray jsonArrayFromCardOne = new JSONArray(cardOne.getString("therapyDTOList"));
        assertEquals(jsonArrayFromCardOne.length(), 1);

        JSONObject therapyJson = jsonArrayFromCardOne.getJSONObject(0);
        assertEquals(therapyJson.getInt(KEY_ID), 1);
        assertEquals(therapyJson.get(KEY_DESCRIPTION), "Some description");
        assertEquals(therapyJson.get(KEY_START_DATE), startDate.toString());
        assertEquals(therapyJson.get(KEY_END_DATE), endDate.toString());
        assertEquals(therapyJson.get(KEY_CARD_ID), 1);
        assertEquals(therapyJson.get(KEY_DOCTOR_ID), 1);
    }

    @Test
    @WithUserDetails("doctor")
    void shouldGetBadRequestStatusWhenNoAuthorizationInGetByPatientId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/cards/{id}", 0))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @WithUserDetails("patient")
    void shouldGetUnauthorizedStatusWhenNoAuthorizationInGetByPatientId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/cards/{id}", 2))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    void shouldGetBadRequestStatusWhenNoAuthenticationInGetByPatientId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/cards/{id}", 2))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @WithUserDetails("doctor")
    void saveCard() throws Exception {

        String cardString = getJsonStringFromFile(REQUEST_BODY_CARD_PATH);
        JSONObject cardJsonFromBody = new JSONObject(cardString);

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/api/v1/cards").content(cardString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        PatientCard patientCardDB = patientCardJpaRepository.findByPatientId(6).get();
        assertEquals(cardJsonFromBody.getInt(KEY_PATIENT_ID), patientCardDB.getPatient().getId());

        JSONObject responseJsonCard = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertEquals(responseJsonCard.getInt(KEY_ID), patientCardDB.getId());
        assertEquals(responseJsonCard.getInt(KEY_PATIENT_ID), patientCardDB.getPatient().getId());

        JSONArray jsonArrayTherapiesDTO = responseJsonCard.getJSONArray("therapyDTOList");
        assertEquals(jsonArrayTherapiesDTO.length(), 0);

    }

    @Test
    @WithUserDetails("doctor")
    void shouldGetBadRequestStatusWhenNoAuthorizationInPost() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/cards"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }


    @Test
    @WithUserDetails("patient")
    void shouldGetUnauthorizedStatusWhenNoAuthorizationInPost() throws Exception {

        String cardString = getJsonStringFromFile(REQUEST_BODY_CARD_PATH);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/cards").content(cardString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    void shouldGetBadRequestStatusWhenNoAuthenticationInPost() throws Exception {

        String cardString = getJsonStringFromFile(REQUEST_BODY_CARD_PATH);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/cards").content(cardString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @WithUserDetails("doctor")
    void shouldGetBadRequestStatusWhenNoAuthenticationInPostWhenCardExist() throws Exception {

        String cardString = getJsonStringFromFile(REQUEST_BODY_EXIST_CARD_PATH);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/cards").content(cardString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    private String getJsonStringFromFile(String path) throws IOException {
        return Files.readString(Paths.get(path));
    }
}