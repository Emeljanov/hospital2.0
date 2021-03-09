package by.emel.anton.api.converter;

import by.emel.anton.api.user.CreateUserRequestDTO;
import by.emel.anton.model.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUserRequestConverter implements Converter<CreateUserRequestDTO, User> {

    private final PasswordEncoder passwordEncoder;

    @Override
    public User convert(CreateUserRequestDTO from) {
        return User.builder()
                .login(from.getLogin())
                .pass(passwordEncoder.encode(from.getPassword()))
                .firstName(from.getFirstName())
                .lastName(from.getLastName())
                .birthday(from.getBirthday())
                .isActive(true)
                .role(from.getRole())
                .build();
    }
}
