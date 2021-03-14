package by.emel.anton.facade.converter;

import by.emel.anton.api.v1.CreateUserRequestDTO;
import by.emel.anton.entity.Role;
import by.emel.anton.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUserRequestConverterTest {

    private final static String LOGIN = "login";
    private final static String PASS = "password";
    private final static String FIRST_NAME = "firstName";
    private final static String LAST_NAME = "lastName";
    private final static LocalDate BIRTHDAY = LocalDate.of(2000, 01, 01);

    @InjectMocks
    private CreateUserRequestConverter createUserRequestConverter;
    @Mock
    private PasswordEncoder passwordEncoder;

    private CreateUserRequestDTO createUserRequestDTO;

    @BeforeEach
    void init() {
        createUserRequestDTO = CreateUserRequestDTO.builder()
                .login(LOGIN)
                .password(PASS)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .birthday(BIRTHDAY)
                .role(Role.ADMIN)
                .build();
    }

    @Test
    void shouldConvert() {
        when(passwordEncoder.encode(PASS)).thenReturn(PASS);

        User user = createUserRequestConverter.convert(createUserRequestDTO);
        assertEquals(user.getLogin(), LOGIN);
        assertEquals(user.getPassword(), PASS);
        assertEquals(user.getBirthday(), BIRTHDAY);
        assertEquals(user.getFirstName(), FIRST_NAME);
        assertEquals(user.getLastName(), LAST_NAME);
        assertEquals(user.getRole(), Role.ADMIN);

        verify(passwordEncoder).encode(PASS);
    }
}