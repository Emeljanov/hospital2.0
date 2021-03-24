package by.emel.anton.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum Role {

    ADMIN(Set.of(Permission.PATIENT, Permission.DOCTOR, Permission.ADMIN)),
    DOCTOR(Set.of(Permission.DOCTOR, Permission.PATIENT)),
    PATIENT(Set.of(Permission.PATIENT));



    private final Set<Permission> permissions;

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
