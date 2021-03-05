package by.emel.anton.model.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum Role {

    ADMIN(Set.of(Permission.READ, Permission.WRITE, Permission.DELETE)),
    DOCTOR(Set.of(Permission.READ, Permission.WRITE)),
    PATIENT(Set.of(Permission.READ));

    private final Set<Permission> permissions;

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
