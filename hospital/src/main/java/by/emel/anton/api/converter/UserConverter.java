package by.emel.anton.api.converter;

import by.emel.anton.api.user.ResponseUserDTO;
import by.emel.anton.model.entity.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Converter<User, ResponseUserDTO> {

    @Override
    public ResponseUserDTO convert(User from) {
        return ResponseUserDTO.builder()
                .id(from.getId())
                .login(from.getLogin())
                .firstName(from.getFirstName())
                .lastName(from.getLastName())
                .roleString(from.getRole().toString())
                .birthday(from.getBirthday())
                .isActive(from.isActive())
                .build();
    }
}
