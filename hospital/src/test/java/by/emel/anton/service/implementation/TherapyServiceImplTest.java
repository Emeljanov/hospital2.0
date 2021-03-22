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
                .id(ID_1).login(LOGIN).pass(PASS)
                .firstName(FIRST_NAME).lastName(LAST_NAME)
                .birthday(BIRTHDAY).isActive(true)
                .role(Role.DOCTOR)
                .build();

        patient = User.builder()
                .id(ID_2).login(LOGIN).pass(PASS)
                .firstName(FIRST_NAME).lastName(LAST_NAME)
                .birthday(BIRTHDAY).isActive(true)
                .role(Role.PATIENT)
                .build();

        patientCard = PatientCard.builder()
                .id(ID_1)
                .therapies(therapies)
                .patient(patient)
                .build();

        therapy = Therapy.builder()
                .id(ID_1).description(DESCRIPTION)
                .card(patientCard).doctor(doctor)
                .startDate(START_DATE).endDate(END_DATE)
                .build();

        therapies.add(therapy);

    }

    @Test
    void shouldSave() {
        when(therapyDao.save(therapy)).thenReturn(therapy);

        Therapy actualTherapy = therapyService.save(therapy);

        assertEquals(therapy, actualTherapy);

        verify(therapyDao).save(therapy);
    }

    @Test
    void shouldFindById() {
        when(therapyDao.findById(ID_1)).thenReturn(Optional.of(therapy));

        Therapy actualTherapy = therapyService.findById(ID_1);

        assertEquals(therapy, actualTherapy);

        verify(therapyDao).findById(ID_1);
    }

    @Test
    void shouldThrowEntityNotFoundHospitalServiceExceptionWhenTherapyNotFoundById() {
        when(therapyDao.findById(ID_1)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundHospitalServiceException.class, () -> therapyService.findById(ID_1));
    }


    @Test
    void shouldFindAll() {
        when(therapyDao.findAll()).thenReturn(therapies);

        List<Therapy> actualTherapies = therapyService.findAll();
        Therapy actualTherapy = actualTherapies.get(0);

        assertEquals(therapy, actualTherapy);

        verify(therapyDao).findAll();
    }

    @Test
    void shouldFindByIdForCardId() {
        when(therapyDao.findByIdForCardId(ID_1, ID_2)).thenReturn(Optional.of(therapy));

        Therapy actualTherapy = therapyService.findByIdForCardId(ID_1, ID_2);

        assertEquals(therapy, actualTherapy);

        verify(therapyDao).findByIdForCardId(ID_1, ID_2);
    }

    @Test
    void shouldThrowEntityNotFoundHospitalServiceExceptionWhenTherapyNotFoundByIdAndCardId() {
        when(therapyDao.findById(ID_1)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundHospitalServiceException.class, () -> therapyService.findById(ID_1));
    }

    @Test
    void shouldFindAllTherapiesForCardId() {
        when(therapyDao.findAllByCardId(ID_2)).thenReturn(therapies);

        List<Therapy> actualTherapies = therapyService.findAllForCardId(ID_2);
        Therapy actualTherapy = actualTherapies.get(0);

        assertEquals(therapy, actualTherapy);

        verify(therapyDao).findAllByCardId(ID_2);
    }
}