package by.emel.anton.service.implementation;

import by.emel.anton.entity.PatientCard;
import by.emel.anton.entity.Role;
import by.emel.anton.entity.Therapy;
import by.emel.anton.entity.User;
import by.emel.anton.repository.PatientCardDao;
import by.emel.anton.service.exception.EntityNotFoundHospitalServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientCardServiceImplTest {

    private final static String LOGIN = "login";
    private final static String PASS = "password";
    private final static String FIRST_NAME = "firstName";
    private final static String LAST_NAME = "lastName";
    private final static LocalDate BIRTHDAY = LocalDate.of(2000, 1, 1);
    private final static LocalDate START_DATE = LocalDate.of(2021, 1, 1);
    private final static LocalDate END_DATE = LocalDate.of(2025, 1, 1);
    private final static int ID_1 = 1;
    private final static int ID_2 = 2;
    private final static String DESCRIPTION = "description";

    @InjectMocks
    private PatientCardServiceImpl patientCardService;

    @Mock
    private PatientCardDao patientCardDao;

    private Therapy therapy;
    private User doctor;
    private User patient;
    private PatientCard patientCard;
    private List<Therapy> therapies;
    private List<PatientCard> cards;

    @BeforeEach
    void setUp() {
        therapies = new ArrayList<>();
        cards = new ArrayList<>();

        doctor = User.builder()
                .id(ID_1)
                .login(LOGIN)
                .pass(PASS)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .birthday(BIRTHDAY)
                .isActive(true)
                .role(Role.DOCTOR)
                .build();

        patient = User.builder()
                .id(ID_2)
                .login(LOGIN)
                .pass(PASS)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .birthday(BIRTHDAY)
                .isActive(true)
                .role(Role.PATIENT)
                .build();

        therapy = Therapy.builder()
                .id(ID_1)
                .description(DESCRIPTION)
                .card(patientCard)
                .doctor(doctor)
//                .patient(patient)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .build();

        therapies.add(therapy);

        patientCard = PatientCard.builder()
                .id(ID_1)
                .therapies(therapies)
                .patient(patient)
                .build();

        cards.add(patientCard);
    }

    @Test
    void shouldSave() {
        when(patientCardDao.save(patientCard)).thenReturn(patientCard);

        PatientCard patientCardFromTest = patientCardService.save(patientCard);
        assertEquals(patientCardFromTest, patientCard);

        verify(patientCardDao).save(patientCard);
    }

    @Test
    void shouldFindById() {
        when(patientCardDao.findById(ID_1)).thenReturn(Optional.of(patientCard));

        PatientCard patientCardFromTest = patientCardService.findById(ID_1);
        assertEquals(patientCardFromTest, patientCard);

        verify(patientCardDao).findById(ID_1);
    }

    @Test
    void shouldThrowEntityNotFoundHospitalServiceExceptionWhenPatientCardNotFoundById() {
        when(patientCardDao.findById(ID_1)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundHospitalServiceException.class, () -> patientCardService.findById(ID_1));
    }

    @Test
    void findByPatientId() {
        when(patientCardDao.findByPatientId(ID_1)).thenReturn(Optional.of(patientCard));

        PatientCard patientCardFromTest = patientCardService.findByPatientId(ID_1);
        assertEquals(patientCardFromTest, patientCard);

        verify(patientCardDao).findByPatientId(ID_1);
    }

    @Test
    void shouldThrowEntityNotFoundHospitalServiceExceptionWhenPatientCardNotFoundByPatientId() {
        when(patientCardDao.findByPatientId(ID_1)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundHospitalServiceException.class, () -> patientCardService.findByPatientId(ID_1));
    }

    @Test
    void shouldFindAll() {
        when(patientCardDao.findAll()).thenReturn(cards);
        List<PatientCard> cardsFromTest = patientCardService.findAll();

        assertEquals(cardsFromTest, cards);
        verify(patientCardDao).findAll();
    }
}