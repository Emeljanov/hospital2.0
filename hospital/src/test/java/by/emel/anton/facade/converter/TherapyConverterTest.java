package by.emel.anton.facade.converter;

import by.emel.anton.api.v1.ResponseTherapyDTO;
import by.emel.anton.entity.PatientCard;
import by.emel.anton.entity.Role;
import by.emel.anton.entity.Therapy;
import by.emel.anton.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TherapyConverterTest {

    private final static String LOGIN = "login";
    private final static String PASS = "password";
    private final static String FIRST_NAME = "firstName";
    private final static String LAST_NAME = "lastName";
    private final static LocalDate BIRTHDAY = LocalDate.of(2000, 01, 01);
    private final static LocalDate START_DATE = LocalDate.of(2021, 1, 1);
    private final static LocalDate END_DATE = LocalDate.of(2025, 1, 1);
    private final static Role ROLE = Role.ADMIN;
    private final static int ID_1 = 1;
    private final static int ID_2 = 2;
    private final static String DESCRIPTION = "description";

    @InjectMocks
    private TherapyConverter therapyConverter;

    private Therapy therapy;
    private User doctor;
    private User patient;
    private PatientCard patientCard;
    private List<Therapy> therapies;

    @BeforeEach
    void init() {

        doctor = User.builder()
                .id(ID_1)
                .login(LOGIN)
                .pass(PASS)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .isActive(true)
                .role(Role.ADMIN)
                .birthday(BIRTHDAY)
                .build();

        patient = User.builder()
                .id(ID_2)
                .login(LOGIN)
                .pass(PASS)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .isActive(true)
                .role(Role.PATIENT)
                .birthday(BIRTHDAY)
                .build();

        therapy = Therapy.builder()
                .id(ID_1)
                .card(patientCard)
                .description(DESCRIPTION)
                .doctor(doctor)
                .patient(patient)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .build();

        therapies = Collections.singletonList(therapy);

        patientCard = PatientCard.builder()
                .id(ID_1)
                .patient(patient)
                .therapies(therapies)
                .build();
    }

    @Test
    void shouldConvert() {
        ResponseTherapyDTO responseTherapyDTO = therapyConverter.convert(therapy);

        assertEquals(responseTherapyDTO.getId(), ID_1);
        assertEquals(responseTherapyDTO.getDescription(), DESCRIPTION);
        assertEquals(responseTherapyDTO.getDoctorId(), ID_1);
        assertEquals(responseTherapyDTO.getPatientId(), ID_2);
        assertEquals(responseTherapyDTO.getEndDate(), END_DATE);
        assertEquals(responseTherapyDTO.getStartDate(), START_DATE);
    }
}