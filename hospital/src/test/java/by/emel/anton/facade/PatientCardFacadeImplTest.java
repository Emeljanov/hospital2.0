package by.emel.anton.facade;

import by.emel.anton.api.v1.ResponsePatientCardDTO;
import by.emel.anton.api.v1.ResponseTherapyDTO;
import by.emel.anton.entity.PatientCard;
import by.emel.anton.entity.Role;
import by.emel.anton.entity.Therapy;
import by.emel.anton.entity.User;
import by.emel.anton.facade.converter.Converter;
import by.emel.anton.service.PatientCardService;
import by.emel.anton.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientCardFacadeImplTest {

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
    private PatientCardFacadeImpl patientCardFacade;
    @Mock
    private PatientCardService patientCardService;
    @Mock
    private UserService userService;
    @Mock
    private Converter<PatientCard, ResponsePatientCardDTO> cardConverter;


    private PatientCard patientCard;
    private ResponsePatientCardDTO responsePatientCardDTO;
    private User patient;
    private User doctor;
    private Therapy therapy;
    private ResponseTherapyDTO responseTherapyDTO;
    private List<Therapy> therapies;
    private List<ResponseTherapyDTO> therapiesDTO;


    @BeforeEach
    void init() {

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

        therapy = Therapy.builder()
                .id(0)
                .description(DESCRIPTION)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .patient(patient)
                .doctor(doctor)
                .card(patientCard)
                .build();

        responseTherapyDTO = ResponseTherapyDTO.builder()
                .id(ID_1)
                .description(DESCRIPTION)
                .patientId(ID_2)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .doctorId(ID_1)
                .build();

        responsePatientCardDTO = ResponsePatientCardDTO.builder()
                .id(ID_1)
                .patientId(ID_2)
                .therapyDTOList(therapiesDTO)
                .build();

        therapies = Collections.singletonList(therapy);
        therapiesDTO = Collections.singletonList(responseTherapyDTO);


        patientCard = PatientCard.builder()
                .id(0) //0 or not
                .patient(patient)
                .therapies(null) //null or not
                .build();

        responsePatientCardDTO = ResponsePatientCardDTO.builder()
                .id(ID_1)
                .patientId(ID_2)
                .therapyDTOList(therapiesDTO)
                .build();

    }

    @Test
    void createForPatientId() {
        when(patientCardService.save(patientCard)).thenReturn(patientCard);
        when(userService.findById(ID_2)).thenReturn(patient);
        when(cardConverter.convert(patientCard)).thenReturn(responsePatientCardDTO);

        ResponsePatientCardDTO responsePatientCardFromFacade = patientCardFacade.createForPatientId(ID_2);

        assertEquals(responsePatientCardFromFacade.getId(), ID_1);
        assertEquals(responsePatientCardFromFacade.getPatientId(), ID_2);
        assertEquals(responsePatientCardFromFacade.getTherapyDTOList(), therapiesDTO);

        verify(patientCardService).save(patientCard);
        verify(userService).findById(ID_2);
        verify(cardConverter).convert(patientCard);

    }

    @Test
    void findById() {

        when(patientCardService.findById(ID_1)).thenReturn(patientCard);
        when(cardConverter.convert(patientCard)).thenReturn(responsePatientCardDTO);

        ResponsePatientCardDTO responsePatientCardFromFacade = patientCardFacade.findById(ID_1);

        assertEquals(responsePatientCardFromFacade.getId(), ID_1);
        assertEquals(responsePatientCardFromFacade.getTherapyDTOList(), therapiesDTO);
        assertEquals(responsePatientCardFromFacade.getPatientId(), ID_2);

        verify(patientCardService).findById(ID_1);
        verify(cardConverter).convert(patientCard);

    }

    @Test
    void findAll() {
    }

    @Test
    void findByPatientId() {
    }
}