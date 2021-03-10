package by.emel.anton.facade.converter;

import by.emel.anton.api.ResponseUserDTO;
import by.emel.anton.entity.User;
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
