package by.emel.anton.service.implementation;

import by.emel.anton.entity.Role;
import by.emel.anton.entity.User;
import by.emel.anton.repository.UserDao;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private final static String LOGIN = "login";
    private final static String PASS = "password";
    private final static String FIRST_NAME = "firstName";
    private final static String LAST_NAME = "lastName";
    private final static LocalDate BIRTHDAY = LocalDate.of(2000, 1, 1);
    private final static Role ROLE = Role.ADMIN;
    private final static int ID_1 = 1;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserDao userDao;

    private User user;
    private List<User> users;

    @BeforeEach
    void setUp() {
        users = new ArrayList<>();

        user = User.builder()
                .id(ID_1).login(LOGIN).pass(PASS)
                .role(ROLE).isActive(true).birthday(BIRTHDAY)
                .firstName(FIRST_NAME).lastName(LAST_NAME)
                .build();

        users.add(user);
    }

    @Test
    void shouldSave() {
        when(userDao.save(user)).thenReturn(user);

        User actualUser = userService.save(user);

        assertEquals(user, actualUser);

        verify(userDao).save(user);
    }

    @Test
    void shouldFindById() {
        when(userDao.findById(ID_1)).thenReturn(Optional.of(user));

        User actualUser = userService.findById(ID_1);

        assertEquals(user, actualUser);

        verify(userDao).findById(ID_1);
    }

    @Test
    void shouldThrowEntityNotFoundHospitalServiceExceptionWhenUserNotFoundById() {
        when(userDao.findById(ID_1)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundHospitalServiceException.class, () -> userService.findById(ID_1));
    }

    @Test
    void shouldFindByLogin() {
        when(userDao.findByLogin(LOGIN)).thenReturn(Optional.of(user));

        User actualUser = userService.findByLogin(LOGIN);

        assertEquals(user, actualUser);

        verify(userDao).findByLogin(LOGIN);
    }


    @Test
    void shouldThrowEntityNotFoundHospitalServiceExceptionWhenUserNotFoundByLogin() {
        when(userDao.findByLogin(LOGIN)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundHospitalServiceException.class, () -> userService.findByLogin(LOGIN));
    }

    @Test
    void shouldFindAll() {
        when(userDao.findAll()).thenReturn(users);

        List<User> actualUsers = userService.findAll();

        assertEquals(users, actualUsers);

        verify(userDao).findAll();
    }

    @Test
    void shouldChangeActiveStatusById() {
        when(userDao.findById(ID_1)).thenReturn(Optional.of(user));
        when(userDao.update(user)).thenReturn(user);

        User actualUser = userService.changeActiveStatusById(ID_1, false);

        assertEquals(actualUser.getId(), ID_1);
        assertEquals(actualUser.getLogin(), LOGIN);
        assertEquals(actualUser.getPass(), PASS);
        assertEquals(actualUser.getRole(), ROLE);
        assertEquals(actualUser.getFirstName(), FIRST_NAME);
        assertEquals(actualUser.getLastName(), LAST_NAME);
        assertFalse(actualUser.isActive());

        verify(userDao).findById(ID_1);
        verify(userDao).update(user);

    }

    @Test
    void shouldUpdate() {
        when(userDao.update(user)).thenReturn(user);

        User actualUser = userService.update(user);

       assertEquals(user,actualUser);

        verify(userDao).update(user);
    }

    @Test
    void shouldDeleteById() {
        userService.deleteById(ID_1);
        verify(userDao).deleteById(ID_1);
    }
}