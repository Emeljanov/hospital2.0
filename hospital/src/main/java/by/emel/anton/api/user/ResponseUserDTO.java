package by.emel.anton.api.user;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class ResponseUserDTO {

    private int id;
    private String login;
    private String roleString;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private boolean isActive;

}
