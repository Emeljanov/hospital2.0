package by.emel.anton.facade.converter;

import by.emel.anton.api.v1.ResponseUserDTO;
import by.emel.anton.entity.Role;
import by.emel.anton.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class UserConverterTest {

    private final String LOGIN = "login";
    private final String PASS = "password";
    private final String FIRST_NAME = "firstName";
    private final String LAST_NAME = "lastName";
    private final LocalDate BIRTHDAY = LocalDate.of(2000, 01, 01);
    private final Role ROLE = Role.ADMIN;
    private final int ID = 1;

    @InjectMocks
    private UserConverter userConverter;

    private User user;

    @BeforeEach
    void init() {
        user = User.builder()
                .id(ID)
                .login(LOGIN)
                .pass(PASS)
                .birthday(BIRTHDAY)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .isActive(true)
                .role(ROLE)
                .build();
    }

    @Test
    void shouldConvert() {

        ResponseUserDTO actualUserDTO = userConverter.convert(user);

        assertEquals(ID, actualUserDTO.getId());
        assertEquals(BIRTHDAY, actualUserDTO.getBirthday());
        assertEquals(FIRST_NAME, actualUserDTO.getFirstName());
        assertEquals(LAST_NAME, actualUserDTO.getLastName());
        assertEquals(LOGIN, actualUserDTO.getLogin());
        assertEquals(ROLE.toString(), actualUserDTO.getRoleString());

    }
}