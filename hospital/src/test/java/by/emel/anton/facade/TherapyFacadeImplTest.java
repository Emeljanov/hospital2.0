package by.emel.anton.facade;

import by.emel.anton.api.v1.RequestTherapyDTO;
import by.emel.anton.api.v1.ResponseTherapyDTO;
import by.emel.anton.entity.PatientCard;
import by.emel.anton.entity.Role;
import by.emel.anton.entity.Therapy;
import by.emel.anton.entity.User;
import by.emel.anton.facade.converter.Converter;
import by.emel.anton.service.PatientCardService;
import by.emel.anton.service.TherapyService;
import by.emel.anton.service.UserService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TherapyFacadeImplTest {
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
    private TherapyFacadeImpl therapyFacade;
    @Mock
    private TherapyService therapyService;
    @Mock
    private UserService userService;
    @Mock
    private PatientCardService patientCardService;
    @Mock
    private Converter<Therapy, ResponseTherapyDTO> therapyConverter;

    private User doctor;
    private User patient;
    private PatientCard patientCard;
    private RequestTherapyDTO requestTherapyDTO;
    private Therapy therapy;
    private ResponseTherapyDTO responseTherapyDTO;
    private List<Therapy> therapies;
    private List<ResponseTherapyDTO> therapiesDTO;

    @BeforeEach
    public void init() {

        doctor = TestUtil.createUser(ID_1, LOGIN, PASS, FIRST_NAME, LAST_NAME, BIRTHDAY, Role.DOCTOR, true);
        patient = TestUtil.createUser(ID_2, LOGIN, PASS, FIRST_NAME, LAST_NAME, BIRTHDAY, Role.PATIENT, true);

        therapies = new ArrayList<>();
        therapiesDTO = new ArrayList<>();

        patientCard = PatientCard.builder()
                .id(ID_1)
                .patient(patient)
                .therapies(therapies)
                .build();

        requestTherapyDTO = RequestTherapyDTO.builder()
                .description(DESCRIPTION).endDate(END_DATE)
                .startDate(START_DATE).patientId(ID_2)
                .build();

        therapy = Therapy.builder()
                .card(patientCard).description(DESCRIPTION)
                .doctor(doctor).startDate(START_DATE)
                .endDate(END_DATE)
                .build();

        responseTherapyDTO = ResponseTherapyDTO.builder()
                .id(ID_1).description(DESCRIPTION)
                .doctorId(ID_1).cardId(ID_1)
                .endDate(END_DATE).startDate(START_DATE)
                .build();

        therapies.add(therapy);
        therapiesDTO.add(responseTherapyDTO);

    }

    @Test
    void shouldSave() {
        when(userService.findById(ID_2)).thenReturn(patient);
        when(patientCardService.findByPatientId(ID_2)).thenReturn(patientCard);
        when(userService.findById(ID_1)).thenReturn(doctor);
        when(therapyService.save(therapy)).thenReturn(therapy);
        when(therapyConverter.convert(therapy)).thenReturn(responseTherapyDTO);

        ResponseTherapyDTO actualTherapyDTO = therapyFacade.save(requestTherapyDTO, ID_1);

        assertEquals(responseTherapyDTO, actualTherapyDTO);

        verify(patientCardService).findByPatientId(ID_2);
        verify(therapyService).save(therapy);
        verify(therapyConverter).convert(therapy);
        verify(userService).findById(ID_1);
        verify(userService).findById(ID_2);
    }

    @Test
    void shouldFind() {
        when(therapyService.findById(ID_1)).thenReturn(therapy);
        when(therapyConverter.convert(therapy)).thenReturn(responseTherapyDTO);

        ResponseTherapyDTO actualTherapyDTO = therapyFacade.find(ID_1);

        assertEquals(responseTherapyDTO, actualTherapyDTO);

        verify(therapyService).findById(ID_1);
        verify(therapyConverter).convert(therapy);
    }

    @Test
    void shouldThrowEntityNotFoundHospitalServiceExceptionWhenTherapyNotFound() {
        EntityNotFoundHospitalServiceException expectedExp = new EntityNotFoundHospitalServiceException("expected message");

        when(therapyService.findById(ID_1)).thenThrow(expectedExp);

        EntityNotFoundHospitalServiceException actualExp = assertThrows(EntityNotFoundHospitalServiceException.class, () -> therapyFacade.find(ID_1));

        assertEquals(expectedExp, actualExp);
    }

    @Test
    void shouldFindAll() {
        when(therapyService.findAll()).thenReturn(therapies);
        when(therapyConverter.convertAll(therapies)).thenReturn(therapiesDTO);

        List<ResponseTherapyDTO> actualTherapiesDTO = therapyFacade.findAll();

        assertNotNull(actualTherapiesDTO);
        assertEquals(therapiesDTO, actualTherapiesDTO);

        verify(therapyService).findAll();
        verify(therapyConverter).convertAll(therapies);
    }

    @Test
    void shouldFindByIdForCardId() {
        when(therapyService.findByIdForCardId(ID_1, ID_2)).thenReturn(therapy);
        when(therapyConverter.convert(therapy)).thenReturn(responseTherapyDTO);

        ResponseTherapyDTO actualTherapyDTO = therapyFacade.findByIdForCardId(ID_1, ID_2);

        assertEquals(responseTherapyDTO, actualTherapyDTO);

        verify(therapyService).findByIdForCardId(ID_1, ID_2);
        verify(therapyConverter).convert(therapy);

    }

    @Test
    void shouldThrowEntityNotFoundHospitalServiceExceptionWhenTherapyNotFoundByIdForCardId() {
        EntityNotFoundHospitalServiceException expectedExp = new EntityNotFoundHospitalServiceException("expected message");

        when(therapyService.findByIdForCardId(ID_1, ID_2)).thenThrow(expectedExp);

        EntityNotFoundHospitalServiceException actualExp = assertThrows(EntityNotFoundHospitalServiceException.class, () -> therapyFacade.findByIdForCardId(ID_1, ID_2));

        assertEquals(expectedExp, actualExp);
    }

    @Test
    void shouldFindAllByPatientId() {
        when(therapyService.findAllForCardId(ID_2)).thenReturn(therapies);
        when(therapyConverter.convertAll(therapies)).thenReturn(therapiesDTO);

        List<ResponseTherapyDTO> actualTherapiesDTO = therapyFacade.findAllByCardId(ID_2);
        ResponseTherapyDTO actualTherapyDTO = actualTherapiesDTO.get(0);

        assertNotNull(actualTherapiesDTO);
        assertEquals(responseTherapyDTO, actualTherapyDTO);

        verify(therapyService).findAllForCardId(ID_2);
        verify(therapyConverter).convertAll(therapies);

    }
}