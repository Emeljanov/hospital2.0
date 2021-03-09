package by.emel.anton.restcontroller;

import by.emel.anton.api.user.CreateUserRequestDTO;
import by.emel.anton.api.user.ResponseUserDTO;
import by.emel.anton.api.user.UserFacade;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static by.emel.anton.restcontroller.Constants.AUTHORITY_ADMIN;
import static by.emel.anton.restcontroller.Constants.AUTHORITY_DOCTOR;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {

    private UserFacade userFacade;

    @GetMapping
    @PreAuthorize(AUTHORITY_DOCTOR)
    public ResponseEntity<List<ResponseUserDTO>> getAllUsers() {
        log.info("Get all users;");
        return ResponseEntity.ok(userFacade.getAllUsers());
    }

    @PreAuthorize(AUTHORITY_DOCTOR)
    @GetMapping("/{id}")
    public ResponseUserDTO getUser(@PathVariable(name = "id") int userId) {
        log.info("Get user with id : {}", userId);
        return userFacade.getUserById(userId);
    }

    @PreAuthorize(AUTHORITY_ADMIN)
    @PostMapping("/add")
    public ResponseUserDTO saveNewUser(@RequestBody CreateUserRequestDTO createUserRequestDTO) {
        log.info("Try to create new user with login : {}", createUserRequestDTO.getLogin());
        return userFacade.saveUser(createUserRequestDTO);
    }

    @PreAuthorize(AUTHORITY_ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(name = "id") int userId) {
        log.info("Delete user with id : {}", userId);
        userFacade.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
