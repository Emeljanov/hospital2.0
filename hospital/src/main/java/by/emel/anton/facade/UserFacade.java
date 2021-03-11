package by.emel.anton.facade;

import by.emel.anton.api.v1.CreateUserRequestDTO;
import by.emel.anton.api.v1.ResponseUserDTO;

import java.util.List;

public interface UserFacade {
    ResponseUserDTO findByLogin(String login);

    ResponseUserDTO findById(int id);

    List<ResponseUserDTO> findAll();

    ResponseUserDTO save(CreateUserRequestDTO createUserRequestDTO);

    void delete(int id);
}
