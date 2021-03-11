package by.emel.anton.restcontroller.v1;


import by.emel.anton.api.v1.RequestUserDTO;
import by.emel.anton.api.v1.ResponseUserDTO;
import by.emel.anton.facade.UserFacade;
import by.emel.anton.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static by.emel.anton.restcontroller.v1.Permissions.AUTHORITY_PATIENT;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthenticationRestController {

    private final static String TOKEN = "token";
    private final static String USER = "user";


    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserFacade userFacade;

    @PostMapping("login")
    public ResponseEntity<?> authenticate(@RequestBody @Valid RequestUserDTO requestUserDTO) {

        String login = requestUserDTO.getLogin();
        String password = requestUserDTO.getPassword();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));

        Map<String, Object> response = new HashMap<>();

        ResponseUserDTO responseUserDTO = userFacade.findByLogin(login);
        String role = responseUserDTO.getRoleString();
        String token = jwtTokenProvider.createToken(login, role);
        response.put(USER, responseUserDTO);
        response.put(TOKEN, token);

        return ResponseEntity.ok(response);
    }

    @PostMapping("logout")
    @PreAuthorize(AUTHORITY_PATIENT)
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
