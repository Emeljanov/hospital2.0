package by.emel.anton.restcontroller;

import by.emel.anton.api.user.ResponseUserDTO;
import by.emel.anton.api.user.UserFacade;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {

    private UserFacade userFacade;


    @GetMapping("/all")
    public ResponseUserDTO getAllUsers() {
     /*   ResponseUserDTO responseUserDTO = userFacade.getUserById(1);
        List<ResponseUserDTO> list = new ArrayList<>();
        list.add(responseUserDTO);*/

//        List<Integer> list = List.of(1,2,3,4,5);
        return userFacade.getUserByLogin("loginFF");
    }

    @PostMapping("/{id}")
    public ResponseUserDTO getUser(@PathVariable(name = "id") int userId) {
        return userFacade.getUserById(userId);
    }
}
