package by.emel.anton.facade;

import by.emel.anton.api.v1.CreateUserRequestDTO;
import by.emel.anton.api.v1.ResponseUserDTO;
import by.emel.anton.entity.Role;
import by.emel.anton.entity.User;
import by.emel.anton.facade.converter.Converter;
import by.emel.anton.service.UserService;
import by.emel.anton.service.exception.EntityNotFoundHospitalServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserFacadeImplTest {

    private final static String LOGIN = "login";
    private final static String PASS = "password";
    private final static String FIRST_NAME = "firstName";
    private final static String LAST_NAME = "lastName";
    private final static LocalDate BIRTHDAY = LocalDate.of(2000, 01, 01);
    private final static Role ROLE = Role.ADMIN;
    private final static int ID = 1;

    private UserFacadeImpl userFacade;
    @Mock
    private UserService userService;

    @Mock
    private Converter<CreateUserRequestDTO, User> createUserRequestDTOUserConverter;

    @Mock
    private Converter<User, ResponseUserDTO> userConverter;

    private User user;
    private ResponseUserDTO responseUserDTO;
    private CreateUserRequestDTO createUserRequestDTO;
    private List<User> users = new ArrayList<>();
    private List<ResponseUserDTO> usersDTO = new ArrayList<>();

    @BeforeEach
    public void init() {

        userFacade = new UserFacadeImpl(userService, userConverter, createUserRequestDTOUserConverter);
        user = TestUtil.createUser(ID, LOGIN, PASS, FIRST_NAME, LAST_NAME, BIRTHDAY, ROLE, true);
        responseUserDTO = TestUtil.createResponseUserDTO(ID, LOGIN, FIRST_NAME, LAST_NAME, ROLE.toString(), true, BIRTHDAY);
        createUserRequestDTO = TestUtil.createUserRequestDTO(LOGIN, FIRST_NAME, LAST_NAME, ROLE, BIRTHDAY, PASS);
        users.add(user);
        usersDTO.add(responseUserDTO);

    }

    @Test
    void shouldFindByLoginWhenExist() {

        when(userService.findByLogin(LOGIN)).thenReturn(user);
        when(userConverter.convert(user)).thenReturn(responseUserDTO);

        ResponseUserDTO actualUserDTO = userFacade.findByLogin(LOGIN);

        assertEquals(responseUserDTO, actualUserDTO);

        verify(userService).findByLogin(LOGIN);
        verify(userConverter).convert(user);
    }

    @Test
    void shouldThrowEntityNotFoundHospitalServiceExceptionWhenUserNotFoundByLogin() {
        final EntityNotFoundHospitalServiceException expectedExp = new EntityNotFoundHospitalServiceException("expected message");

        when(userService.findByLogin(LOGIN)).thenThrow(expectedExp);

        EntityNotFoundHospitalServiceException actualExp = assertThrows(EntityNotFoundHospitalServiceException.class, () -> userFacade.findByLogin(LOGIN));

        assertEquals(expectedExp, actualExp);
    }


    @Test
    void shouldFindByIdWhenUserExist() {

        when(userService.findById(ID)).thenReturn(user);
        when(userConverter.convert(user)).thenReturn(responseUserDTO);

        ResponseUserDTO actualUserDTO = userFacade.findById(ID);

        assertEquals(responseUserDTO, actualUserDTO);

        verify(userService).findById(ID);
        verify(userConverter).convert(user);

    }

    @Test
    void shouldThrowEntityNotFoundHospitalServiceExceptionWhenUserNotFoundById() {
        final EntityNotFoundHospitalServiceException expectedExp = new EntityNotFoundHospitalServiceException("expected message");

        when(userService.findById(ID)).thenThrow(expectedExp);

        EntityNotFoundHospitalServiceException actualExp = assertThrows(EntityNotFoundHospitalServiceException.class, () -> userFacade.findById(ID));

        assertEquals(expectedExp, actualExp);
    }

    @Test
    void shouldFindAllWhenUsersExist() {

        when(userService.findAll()).thenReturn(users);
        when(userConverter.convertAll(users)).thenReturn(usersDTO);

        List<ResponseUserDTO> actualUsersDTO = userFacade.findAll();

        assertNotNull(actualUsersDTO);
        assertEquals(usersDTO, actualUsersDTO);

        verify(userService).findAll();
        verify(userConverter).convertAll(users);

    }

    @Test
    void shouldSaveUser() {

        when(createUserRequestDTOUserConverter.convert(createUserRequestDTO)).thenReturn(user);
        when(userService.save(user)).thenReturn(user);
        when(userConverter.convert(user)).thenReturn(responseUserDTO);

        ResponseUserDTO actualUserDTO = userFacade.save(createUserRequestDTO);

        assertEquals(responseUserDTO, actualUserDTO);

        verify(createUserRequestDTOUserConverter).convert(createUserRequestDTO);
        verify(userService).save(user);
        verify(userConverter).convert(user);

    }

    @Test
    void shouldDeleteUser() {
        userFacade.delete(ID);

        verify(userService).deleteById(ID);
    }

    @Test
    void changeUserActiveStatus() {
        when(userService.changeActiveStatusById(ID, true)).thenReturn(user);
        when(userConverter.convert(user)).thenReturn(responseUserDTO);

        ResponseUserDTO actualUserDTO = userFacade.changeActiveStatus(ID, true);

        assertEquals(responseUserDTO, actualUserDTO);

        verify(userService).changeActiveStatusById(ID, true);
        verify(userConverter).convert(user);
    }
}