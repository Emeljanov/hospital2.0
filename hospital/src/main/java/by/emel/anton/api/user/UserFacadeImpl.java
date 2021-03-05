package by.emel.anton.api.user;

import by.emel.anton.api.converter.Converter;
import by.emel.anton.model.entity.user.User;
import by.emel.anton.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserFacadeImpl implements UserFacade {

    private UserService userService;
    private Converter<User, ResponseUserDTO> userConverter;

    @Override
    public ResponseUserDTO getUserByLogin(String login) {
        return userConverter.convert(userService.getUserByLogin(login));
    }

    @Override
    public ResponseUserDTO getUserById(int id) {
        return userConverter.convert(userService.getUserById(id));
    }
}
