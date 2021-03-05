package by.emel.anton.api.user;

public interface UserFacade {
    ResponseUserDTO getUserByLogin(String login);

    ResponseUserDTO getUserById(int id);
}
