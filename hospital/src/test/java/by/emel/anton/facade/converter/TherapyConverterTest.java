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
import java.util.ArrayList;
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

        therapies = new ArrayList<>();

        doctor = User.builder()
                .id(ID_1).login(LOGIN).pass(PASS)
                .firstName(FIRST_NAME).lastName(LAST_NAME)
                .isActive(true).role(Role.ADMIN)
                .birthday(BIRTHDAY)
                .build();

        patient = User.builder()
                .id(ID_2).login(LOGIN).pass(PASS)
                .firstName(FIRST_NAME).lastName(LAST_NAME)
                .isActive(true).role(Role.PATIENT)
                .birthday(BIRTHDAY)
                .build();

        patientCard = PatientCard.builder()
                .id(ID_1).patient(patient)
                .therapies(therapies)
                .build();

        therapy = Therapy.builder()
                .id(ID_1).card(patientCard)
                .description(DESCRIPTION).doctor(doctor)
                .startDate(START_DATE).endDate(END_DATE)
                .build();

        therapies.add(therapy);

    }

    @Test
    void shouldConvert() {
        ResponseTherapyDTO actualTherapyDTO = therapyConverter.convert(therapy);

        assertEquals(ID_1, actualTherapyDTO.getId());
        assertEquals(DESCRIPTION, actualTherapyDTO.getDescription());
        assertEquals(ID_1, actualTherapyDTO.getDoctorId());
        assertEquals(END_DATE, actualTherapyDTO.getEndDate());
        assertEquals(START_DATE, actualTherapyDTO.getStartDate());
        assertEquals(ID_1, actualTherapyDTO.getCardId());
    }
}