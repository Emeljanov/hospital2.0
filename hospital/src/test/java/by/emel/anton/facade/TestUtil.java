package by.emel.anton.facade;

import by.emel.anton.api.v1.CreateUserRequestDTO;
import by.emel.anton.api.v1.ResponseUserDTO;
import by.emel.anton.entity.Role;
import by.emel.anton.entity.User;

import java.time.LocalDate;

public class TestUtil {

    public static User createUser(int id, String login, String pass, String firstName, String lastName, LocalDate birthday, Role role, boolean isActive) {
        return User.builder()
                .id(id)
                .role(role)
                .isActive(isActive)
                .birthday(birthday)
                .lastName(lastName)
                .firstName(firstName)
                .pass(pass)
                .login(login)
                .build();
    }

    public static ResponseUserDTO createResponseUserDTO(int id, String login, String firstName, String lastName, String roleString, boolean isActive, LocalDate birthday) {
        return ResponseUserDTO.builder()
                .id(id)
                .login(login)
                .firstName(firstName)
                .lastName(lastName)
                .roleString(roleString)
                .isActive(isActive)
                .birthday(birthday)
                .build();
    }

    public static CreateUserRequestDTO createUserRequestDTO(String login, String firstName, String lastName, Role role, LocalDate birthday, String pass) {
        return CreateUserRequestDTO.builder()
                .login(login)
                .firstName(firstName)
                .lastName(lastName)
                .role(role)
                .birthday(birthday)
                .password(pass)
                .build();
    }
}
