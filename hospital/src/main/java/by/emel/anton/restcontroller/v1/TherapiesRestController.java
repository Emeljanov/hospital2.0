package by.emel.anton.restcontroller.v1;

import by.emel.anton.api.RequestTherapyDTO;
import by.emel.anton.api.ResponseTherapyDTO;
import by.emel.anton.entity.User;
import by.emel.anton.facade.TherapyFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static by.emel.anton.restcontroller.v1.Constants.AUTHORITY_DOCTOR;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/therapies")
public class TherapiesRestController {

    private final TherapyFacade therapyFacade;

    @PreAuthorize(AUTHORITY_DOCTOR)
    @GetMapping()
    public ResponseEntity<List<ResponseTherapyDTO>> findAllTherapies() {
        log.info("Get all therapies");
        return ResponseEntity.ok(therapyFacade.getAllTherapies());
    }

    @PreAuthorize(AUTHORITY_DOCTOR)
    @GetMapping("{id}")
    public ResponseTherapyDTO findTherapy(@PathVariable(name = "id") int therapyId) {
        log.info("Get therapy by id : {}", therapyId);
        return therapyFacade.getTherapy(therapyId);
    }

    @PreAuthorize(AUTHORITY_DOCTOR)
    @PostMapping()
    public ResponseTherapyDTO saveTherapy(@RequestBody RequestTherapyDTO requestTherapyDTO, Principal principal) {
        log.info("Add new therapy with description : {}, and patient id : {}", requestTherapyDTO.getDescription(), requestTherapyDTO.getPatientId());
        User doctor = (User) principal;
        return therapyFacade.saveTherapy(requestTherapyDTO, doctor.getId());
    }
}
