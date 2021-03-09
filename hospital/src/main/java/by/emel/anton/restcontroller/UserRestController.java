package by.emel.anton.restcontroller;

import by.emel.anton.api.user.CreateUserRequestDTO;
import by.emel.anton.api.user.ResponseUserDTO;
import by.emel.anton.api.user.UserFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {

    private UserFacade userFacade;

    @GetMapping
    @PreAuthorize("hasAuthority('permission:doctor')")
    public ResponseEntity<List<ResponseUserDTO>> getAllUsers() {
        return ResponseEntity.ok(userFacade.getAllUsers());
    }

    @PreAuthorize("hasAuthority('permission:doctor')")
    @PostMapping("/{id}")
    public ResponseUserDTO getUser(@PathVariable(name = "id") int userId) {
        return userFacade.getUserById(userId);
    }

    @PreAuthorize("hasAuthority('permission:doctor')")
    @PostMapping("/add")
    public ResponseUserDTO saveNewUser(@RequestBody CreateUserRequestDTO createUserRequestDTO) {
       return userFacade.saveUser(createUserRequestDTO);
    }
}
