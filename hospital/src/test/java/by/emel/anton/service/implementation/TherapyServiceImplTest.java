package by.emel.anton.service.implementation;

import by.emel.anton.entity.PatientCard;
import by.emel.anton.entity.Role;
import by.emel.anton.entity.Therapy;
import by.emel.anton.entity.User;
import by.emel.anton.repository.TherapyDao;
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
class TherapyServiceImplTest {

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
    private TherapyServiceImpl therapyService;

    @Mock
    private TherapyDao therapyDao;

    private Therapy therapy;
    private User doctor;
    private User patient;
    private PatientCard patientCard;
    private List<Therapy> therapies;

    @BeforeEach
    void setUp() {
        therapies = new ArrayList<>();

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

        patientCard = PatientCard.builder()
                .id(ID_1)
                .therapies(therapies)
                .patient(patient)
                .build();

        therapy = Therapy.builder()
                .id(ID_1)
                .description(DESCRIPTION)
                .card(patientCard)
                .doctor(doctor)
                .patient(patient)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .build();

        therapies.add(therapy);

    }

    @Test
    void shouldSave() {
        when(therapyDao.save(therapy)).thenReturn(therapy);

        Therapy therapyFromTest = therapyService.save(therapy);

        assertEquals(therapyFromTest.getId(), ID_1);
        assertEquals(therapyFromTest.getDescription(), DESCRIPTION);
        assertEquals(therapyFromTest.getEndDate(), END_DATE);
        assertEquals(therapyFromTest.getStartDate(), START_DATE);
        assertEquals(therapyFromTest.getCard(), patientCard);

        assertEquals(therapyFromTest.getDoctor().getId(), ID_1);
        assertEquals(therapyFromTest.getDoctor().getLastName(), LAST_NAME);
        assertEquals(therapyFromTest.getDoctor().getFirstName(), FIRST_NAME);
        assertEquals(therapyFromTest.getDoctor().getBirthday(), BIRTHDAY);
        assertEquals(therapyFromTest.getDoctor().getRole(), Role.DOCTOR);
        assertEquals(therapyFromTest.getDoctor().getLogin(), LOGIN);
        assertEquals(therapyFromTest.getDoctor().getPass(), PASS);

        assertEquals(therapyFromTest.getPatient().getId(), ID_2);
        assertEquals(therapyFromTest.getPatient().getLogin(), LOGIN);
        assertEquals(therapyFromTest.getPatient().getPass(), PASS);
        assertEquals(therapyFromTest.getPatient().getFirstName(), FIRST_NAME);
        assertEquals(therapyFromTest.getPatient().getLastName(), LAST_NAME);
        assertEquals(therapyFromTest.getPatient().getRole(), Role.PATIENT);
        assertEquals(therapyFromTest.getPatient().getBirthday(), BIRTHDAY);


        verify(therapyDao).save(therapy);
    }

    @Test
    void shouldFindById() {
        when(therapyDao.findById(ID_1)).thenReturn(Optional.of(therapy));

        Therapy therapyFromTest = therapyService.findById(ID_1);
        assertEquals(therapyFromTest.getId(), ID_1);
        assertEquals(therapyFromTest.getCard(), patientCard);
        assertEquals(therapyFromTest.getDoctor(), doctor);
        assertEquals(therapyFromTest.getPatient(), patient);
        assertEquals(therapyFromTest.getDescription(), DESCRIPTION);
        assertEquals(therapyFromTest.getEndDate(), END_DATE);
        assertEquals(therapyFromTest.getStartDate(), START_DATE);

        verify(therapyDao).findById(ID_1);
    }

    @Test
    void shouldThrowEntityNotFoundHospitalServiceExceptionWhenTherapyNotFoundById() {
        when(therapyDao.findById(ID_1)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundHospitalServiceException.class, () -> therapyService.findById(ID_1));
    }


    @Test
    void findAll() {
        when(therapyDao.findAll()).thenReturn(therapies);

        List<Therapy> therapiesFromTest = therapyService.findAll();
        Therapy therapyFromTest = therapiesFromTest.get(0);

        assertEquals(therapyFromTest.getId(), ID_1);
        assertEquals(therapyFromTest.getCard(), patientCard);
        assertEquals(therapyFromTest.getDoctor(), doctor);
        assertEquals(therapyFromTest.getPatient(), patient);
        assertEquals(therapyFromTest.getDescription(), DESCRIPTION);
        assertEquals(therapyFromTest.getEndDate(), END_DATE);
        assertEquals(therapyFromTest.getStartDate(), START_DATE);

        verify(therapyDao).findAll();
    }

    @Test
    void shouldFindByIdForPatientId() {
        when(therapyDao.findByIdForPatientId(ID_1, ID_2)).thenReturn(Optional.of(therapy));

        Therapy therapyFromTest = therapyService.findByIdForPatientId(ID_1, ID_2);

        assertEquals(therapyFromTest.getId(), ID_1);
        assertEquals(therapyFromTest.getCard(), patientCard);
        assertEquals(therapyFromTest.getDoctor(), doctor);
        assertEquals(therapyFromTest.getPatient(), patient);
        assertEquals(therapyFromTest.getDescription(), DESCRIPTION);
        assertEquals(therapyFromTest.getEndDate(), END_DATE);
        assertEquals(therapyFromTest.getStartDate(), START_DATE);

        verify(therapyDao).findByIdForPatientId(ID_1, ID_2);
    }

    @Test
    void shouldThrowEntityNotFoundHospitalServiceExceptionWhenTherapyNotFoundByIdAndPatientId() {
        when(therapyDao.findById(ID_1)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundHospitalServiceException.class, () -> therapyService.findById(ID_1));
    }

    @Test
    void shouldFindAllForPatientId() {
        when(therapyDao.findAllByPatientId(ID_2)).thenReturn(therapies);

        List<Therapy> therapiesFromTest = therapyService.findAllForPatientId(ID_2);
        Therapy therapyFromTest = therapiesFromTest.get(0);

        assertEquals(therapyFromTest.getId(), ID_1);
        assertEquals(therapyFromTest.getCard(), patientCard);
        assertEquals(therapyFromTest.getDoctor(), doctor);
        assertEquals(therapyFromTest.getPatient(), patient);
        assertEquals(therapyFromTest.getDescription(), DESCRIPTION);
        assertEquals(therapyFromTest.getEndDate(), END_DATE);
        assertEquals(therapyFromTest.getStartDate(), START_DATE);

        verify(therapyDao).findAllByPatientId(ID_2);
    }
}