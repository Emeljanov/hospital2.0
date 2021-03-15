package by.emel.anton.restcontroller.v1;

import by.emel.anton.api.v1.CreateUserRequestDTO;
import by.emel.anton.api.v1.ResponseUserDTO;
import by.emel.anton.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static by.emel.anton.restcontroller.v1.Permissions.AUTHORITY_ADMIN;
import static by.emel.anton.restcontroller.v1.Permissions.AUTHORITY_DOCTOR;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UsersRestController {

    private final UserFacade userFacade;

    @GetMapping
    @PreAuthorize(AUTHORITY_DOCTOR)
    public ResponseEntity<List<ResponseUserDTO>> findAll() {
        return ResponseEntity.ok(userFacade.findAll());
    }

    @PreAuthorize(AUTHORITY_DOCTOR)
    @GetMapping("{id}")
    public ResponseUserDTO findUserById(@PathVariable(name = "id") int userId) {
        return userFacade.findById(userId);
    }

    @PreAuthorize(AUTHORITY_ADMIN)
    @PostMapping()
    public ResponseUserDTO saveUser(@RequestBody @Valid CreateUserRequestDTO createUserRequestDTO) {
        return userFacade.save(createUserRequestDTO);
    }

    @PreAuthorize(AUTHORITY_ADMIN)
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteUserById(@PathVariable(name = "id") int userId) {
        userFacade.delete(userId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize(AUTHORITY_ADMIN)
    @PutMapping("change/status/{userId}/{isActive}")
    public ResponseUserDTO changeUserStatus(@PathVariable(name = "userId") int userId, @PathVariable(name = "isActive") boolean isActive) {
        return userFacade.changeActiveStatus(userId, isActive);
    }
}
