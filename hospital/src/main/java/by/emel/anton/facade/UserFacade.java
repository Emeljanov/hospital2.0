package by.emel.anton.facade;

import by.emel.anton.api.CreateUserRequestDTO;
import by.emel.anton.api.ResponseUserDTO;

import java.util.List;

public interface UserFacade {
    ResponseUserDTO getUserByLogin(String login);

    ResponseUserDTO getUserById(int id);

    List<ResponseUserDTO> getAllUsers();

    ResponseUserDTO saveUser(CreateUserRequestDTO createUserRequestDTO);

    void deleteUser(int id);
}
