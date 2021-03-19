package by.emel.anton.restcontroller.v1;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = {"classpath:before-each.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class PatientsRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
    @WithUserDetails("patient")
    void shouldFindAllTherapies() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/patients/{cardId}/therapies", 1))
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
    @WithUserDetails("another_patient")
    void shouldGetUnauthorizedStatusWhenNoAuthorizationInGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/patients/{cardId}/therapies", 1))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    void shouldGetBadRequestStatusWhenNoAuthenticationInGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/patients/{cardId}/therapies", 1))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @WithUserDetails("patient")
    void shouldFindTherapyById() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/patients/{cardId}/therapies/{therapyId}", 1, 1))
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
    @WithUserDetails("another_patient")
    void shouldGetUnauthorizedStatusWhenNoAuthorizationInGetTherapyById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/patients/{cardId}/therapies/{1}", 1, 1))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    @WithUserDetails("patient")
    void shouldGetBadRequestStatusWhenNoAuthorizationInGetTherapyById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/patients/{cardId}/therapies/{1}", 1, 0))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void shouldGetBadRequestStatusWhenNoAuthenticationInGetTherapyById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/patients/{cardId}/therapies/{1}", 1, 1))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @WithUserDetails("patient")
    void shouldFindPatientCard() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/patients/{patientId}/card", 2))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject cardJson = new JSONObject(mvcResult.getResponse().getContentAsString());

        assertEquals(cardJson.getInt(KEY_ID), 1);
        assertEquals(cardJson.getInt(KEY_PATIENT_ID), 2);

        JSONArray jsonArray = new JSONArray(cardJson.getString("therapyDTOList"));
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
    @WithUserDetails("another_patient")
    void shouldGetUnauthorizedStatusWhenNoAuthorizationInGetPatientCard() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/patients/{patientId}/card", 2))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    @WithUserDetails("patient_without_card")
    void shouldGetBadRequestStatusWhenCardNotExistInGetPatientCard() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/patients/{patientId}/card", 6))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void shouldGetForbiddenStatusWhenNoAuthenticationInGetPatientCard() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/patients/{patientId}/card", 2))
                .andExpect(status().isForbidden())
                .andReturn();
    }
}