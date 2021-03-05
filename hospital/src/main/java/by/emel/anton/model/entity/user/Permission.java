package by.emel.anton.model.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Permission {

    READ("permission:read"),
    WRITE("permission:write"),
    DELETE("permission:delete");

    private final String permission;

}
