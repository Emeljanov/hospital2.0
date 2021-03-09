package by.emel.anton.model.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum Role {

    ADMIN(Set.of(Permission.SIMPLE, Permission.DOCTOR, Permission.ADMIN)),
    DOCTOR(Set.of(Permission.DOCTOR, Permission.SIMPLE)),
    PATIENT(Set.of(Permission.SIMPLE));

    private final Set<Permission> permissions;

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
