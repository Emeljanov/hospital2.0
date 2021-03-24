package by.emel.anton.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Permission {

    PATIENT("permission:patient"),
    DOCTOR("permission:doctor"),
    ADMIN("permission:admin");



    private final String permission;

}
