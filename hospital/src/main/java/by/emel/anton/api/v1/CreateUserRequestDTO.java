package by.emel.anton.api.v1;

import by.emel.anton.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class CreateUserRequestDTO {

    @NotNull(message = "field login = null")
    private String login;
    @NotNull(message = "field password = null")
    private String password;
    @NotNull(message = "field roleString = null")
    private Role role;
    @NotNull(message = "field firstName = null")
    private String firstName;
    @NotNull(message = "field lastName = null")
    private String lastName;
    @NotNull(message = "field birthday = null")
    private LocalDate birthday;

}
