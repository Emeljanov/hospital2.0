package by.emel.anton.restcontroller.v1;

import by.emel.anton.api.v1.RequestTherapyDTO;
import by.emel.anton.api.v1.ResponseTherapyDTO;
import by.emel.anton.entity.User;
import by.emel.anton.facade.TherapyFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static by.emel.anton.restcontroller.v1.Permissions.AUTHORITY_DOCTOR;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/therapies")
public class TherapiesRestController {

    private final TherapyFacade therapyFacade;

    @PreAuthorize(AUTHORITY_DOCTOR)
    @GetMapping()
    public ResponseEntity<List<ResponseTherapyDTO>> findAllTherapies() {
        return ResponseEntity.ok(therapyFacade.findAll());
    }

    @PreAuthorize(AUTHORITY_DOCTOR)
    @GetMapping("{id}")
    public ResponseTherapyDTO findTherapy(@PathVariable(name = "id") int therapyId) {
        return therapyFacade.find(therapyId);
    }

    @PreAuthorize(AUTHORITY_DOCTOR)
    @PostMapping()
    public ResponseTherapyDTO saveTherapy(@RequestBody RequestTherapyDTO requestTherapyDTO, Authentication authentication) {
        User doctor = (User) authentication.getPrincipal();
        return therapyFacade.save(requestTherapyDTO, doctor.getId());
    }
}
