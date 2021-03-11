package by.emel.anton.facade;

import by.emel.anton.api.v1.CreateUserRequestDTO;
import by.emel.anton.api.v1.ResponseUserDTO;
import by.emel.anton.entity.User;
import by.emel.anton.facade.converter.Converter;
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
    public ResponseUserDTO findByLogin(String login) {
        return userConverter.convert(userService.findByLogin(login));
    }

    @Override
    public ResponseUserDTO findById(int id) {
        return userConverter.convert(userService.findById(id));
    }

    @Override
    public List<ResponseUserDTO> findAll() {
        return userConverter.convertAll(userService.findAll());
    }

    @Override
    public ResponseUserDTO save(CreateUserRequestDTO createUserRequestDTO) {

        User user = createUserRequestDTOUserConverter.convert(createUserRequestDTO);
        return userConverter.convert(userService.save(user));
    }

    @Override
    public void delete(int id) {
        userService.deleteById(id);
    }

    @Override
    public ResponseUserDTO changeActiveStatus(int userId, boolean isActive) {
        return userConverter.convert(userService.changeActiveStatusById(userId, isActive));
    }
}
