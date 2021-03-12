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
import java.util.Arrays;
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


        therapies = Arrays.asList(therapy);

        patientCard = PatientCard.builder()
                .id(ID_1)
                .patient(patient)
                .therapies(therapies)
                .build();

        requestTherapyDTO = RequestTherapyDTO.builder()
                .description(DESCRIPTION)
                .endDate(END_DATE)
                .startDate(START_DATE)
                .patientId(ID_2)
                .build();

        therapy = Therapy.builder()
                .card(patientCard)
                .description(DESCRIPTION)
                .doctor(doctor)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .patient(patient)
                .build();

        responseTherapyDTO = ResponseTherapyDTO.builder()
                .id(ID_1)
                .description(DESCRIPTION)
                .doctorId(ID_1)
                .endDate(END_DATE)
                .startDate(START_DATE)
                .patientId(ID_2)
                .build();

        therapiesDTO = Arrays.asList(responseTherapyDTO);

    }

    @Test
    void shouldSave() {
        when(userService.findById(ID_2)).thenReturn(patient);
        when(patientCardService.findByPatientId(ID_2)).thenReturn(patientCard);
        when(userService.findById(ID_1)).thenReturn(doctor);
        when(therapyService.save(therapy)).thenReturn(therapy);
        when(therapyConverter.convert(therapy)).thenReturn(responseTherapyDTO);

        ResponseTherapyDTO responseTherapyFromFacade = therapyFacade.save(requestTherapyDTO, ID_1);

        assertEquals(responseTherapyFromFacade.getId(), ID_1);
        assertEquals(responseTherapyFromFacade.getDescription(), DESCRIPTION);
        assertEquals(responseTherapyFromFacade.getDoctorId(), ID_1);
        assertEquals(responseTherapyFromFacade.getPatientId(), ID_2);
        assertEquals(responseTherapyFromFacade.getEndDate(), END_DATE);
        assertEquals(responseTherapyFromFacade.getStartDate(), START_DATE);

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

        ResponseTherapyDTO responseTherapyFromFacade = therapyFacade.find(ID_1);

        assertEquals(responseTherapyFromFacade.getId(), ID_1);
        assertEquals(responseTherapyFromFacade.getDescription(), DESCRIPTION);
        assertEquals(responseTherapyFromFacade.getDoctorId(), ID_1);
        assertEquals(responseTherapyFromFacade.getPatientId(), ID_2);
        assertEquals(responseTherapyFromFacade.getEndDate(), END_DATE);
        assertEquals(responseTherapyFromFacade.getStartDate(), START_DATE);

        verify(therapyService).findById(ID_1);
        verify(therapyConverter).convert(therapy);

    }

    @Test
    void shouldThrowEntityNotFoundHospitalServiceExceptionWhenTherapyNotFound() {
        when(therapyService.findById(ID_1)).thenThrow(EntityNotFoundHospitalServiceException.class);
        assertThrows(EntityNotFoundHospitalServiceException.class, () -> therapyFacade.find(ID_1));
    }

    @Test
    void shouldFindAll() {
        when(therapyService.findAll()).thenReturn(therapies);
        when(therapyConverter.convertAll(therapies)).thenReturn(therapiesDTO);

        List<ResponseTherapyDTO> therapiesDtoFromFacade = therapyFacade.findAll();

        assertNotNull(therapiesDtoFromFacade);

        ResponseTherapyDTO responseTherapyFromFacade = therapiesDtoFromFacade.get(0);

        assertEquals(responseTherapyFromFacade.getId(), ID_1);
        assertEquals(responseTherapyFromFacade.getDescription(), DESCRIPTION);
        assertEquals(responseTherapyFromFacade.getDoctorId(), ID_1);
        assertEquals(responseTherapyFromFacade.getPatientId(), ID_2);
        assertEquals(responseTherapyFromFacade.getEndDate(), END_DATE);
        assertEquals(responseTherapyFromFacade.getStartDate(), START_DATE);

        verify(therapyService).findAll();
        verify(therapyConverter).convertAll(therapies);
    }

    @Test
    void shouldFindByIdForPatientId() {
        when(therapyService.findByIdForPatientId(ID_1, ID_2)).thenReturn(therapy);
        when(therapyConverter.convert(therapy)).thenReturn(responseTherapyDTO);

        ResponseTherapyDTO responseTherapyFromFacade = therapyFacade.findByIdForPatientId(ID_1, ID_2);

        assertEquals(responseTherapyFromFacade.getId(), ID_1);
        assertEquals(responseTherapyFromFacade.getDescription(), DESCRIPTION);
        assertEquals(responseTherapyFromFacade.getDoctorId(), ID_1);
        assertEquals(responseTherapyFromFacade.getPatientId(), ID_2);
        assertEquals(responseTherapyFromFacade.getEndDate(), END_DATE);
        assertEquals(responseTherapyFromFacade.getStartDate(), START_DATE);

        verify(therapyService).findByIdForPatientId(ID_1, ID_2);
        verify(therapyConverter).convert(therapy);

    }

    @Test
    void shouldThrowEntityNotFoundHospitalServiceExceptionWhenTherapyNotFoundByIdForPatientId() {
        when(therapyService.findByIdForPatientId(ID_1, ID_2)).thenThrow(EntityNotFoundHospitalServiceException.class);
        assertThrows(EntityNotFoundHospitalServiceException.class, () -> therapyFacade.findByIdForPatientId(ID_1, ID_2));
    }

    @Test
    void shouldFindAllByPatientId() {
        when(therapyService.getAllForPatientId(ID_2)).thenReturn(therapies);
        when(therapyConverter.convertAll(therapies)).thenReturn(therapiesDTO);

        List<ResponseTherapyDTO> therapiesDtoFromFacade = therapyFacade.findAllByPatientId(ID_2);

        assertNotNull(therapiesDtoFromFacade);
        ResponseTherapyDTO responseTherapyFromFacade = therapiesDtoFromFacade.get(0);

        assertEquals(responseTherapyFromFacade.getId(), ID_1);
        assertEquals(responseTherapyFromFacade.getDescription(), DESCRIPTION);
        assertEquals(responseTherapyFromFacade.getDoctorId(), ID_1);
        assertEquals(responseTherapyFromFacade.getPatientId(), ID_2);
        assertEquals(responseTherapyFromFacade.getEndDate(), END_DATE);
        assertEquals(responseTherapyFromFacade.getStartDate(), START_DATE);

        verify(therapyService).getAllForPatientId(ID_2);
        verify(therapyConverter).convertAll(therapies);

    }
}