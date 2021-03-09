package by.emel.anton.model.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Permission {

    SIMPLE("permission:simple"),
    DOCTOR("permission:doctor"),
    ADMIN("permission:admin");

    private final String permission;

}
