package by.emel.anton.api.user;

import java.util.List;

public interface UserFacade {
    ResponseUserDTO getUserByLogin(String login);

    ResponseUserDTO getUserById(int id);

    List<ResponseUserDTO> getAllUsers();

    ResponseUserDTO saveUser(CreateUserRequestDTO createUserRequestDTO);

    void deleteUser(int id);
}
