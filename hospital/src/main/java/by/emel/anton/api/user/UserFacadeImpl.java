package by.emel.anton.api.user;

import by.emel.anton.api.converter.Converter;
import by.emel.anton.model.entity.user.User;
import by.emel.anton.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class UserFacadeImpl implements UserFacade {

    private UserService userService;
    private Converter<User, ResponseUserDTO> userConverter;
    private Converter<CreateUserRequestDTO, User> createUserRequestDTOUserConverter;

    @Override
    public ResponseUserDTO getUserByLogin(String login) {
        return userConverter.convert(userService.getUserByLogin(login));
    }

    @Override
    public ResponseUserDTO getUserById(int id) {
        return userConverter.convert(userService.getUserById(id));
    }

    @Override
    public List<ResponseUserDTO> getAllUsers() {
        return userConverter.convertAll(userService.getAllUsers());
    }

    @Override
    public ResponseUserDTO saveUser(CreateUserRequestDTO createUserRequestDTO) {

        User user = createUserRequestDTOUserConverter.convert(createUserRequestDTO);

        return userConverter.convert(userService.saveUser(user));
    }
}
