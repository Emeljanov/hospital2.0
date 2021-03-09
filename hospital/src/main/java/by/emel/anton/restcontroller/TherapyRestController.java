package by.emel.anton.restcontroller;

import by.emel.anton.api.therapy.RequestTherapyDTO;
import by.emel.anton.api.therapy.ResponseTherapyDTO;
import by.emel.anton.api.therapy.TherapyFacade;
import by.emel.anton.model.entity.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static by.emel.anton.restcontroller.Constants.AUTHORITY_DOCTOR;
import static by.emel.anton.restcontroller.Constants.AUTHORITY_SIMPLE;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/therapies")
public class TherapyRestController {

    private final TherapyFacade therapyFacade;

    @PreAuthorize(AUTHORITY_DOCTOR)
    @GetMapping
    public ResponseEntity<List<ResponseTherapyDTO>> getAllTherapies() {
        log.info("Get all therapies");
        return ResponseEntity.ok(therapyFacade.getAllTherapies());
    }

    @PreAuthorize(AUTHORITY_DOCTOR)
    @GetMapping("/{id}")
    public ResponseTherapyDTO getTherapy(@PathVariable(name = "id") int therapyId) {
        log.info("Get therapy by id : {}", therapyId);
        return therapyFacade.getTherapy(therapyId);
    }

    @PreAuthorize(AUTHORITY_SIMPLE)
    @GetMapping("/get/{id}")
    public ResponseTherapyDTO getTherapyForPatient(@PathVariable(name = "id") int therapyId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int patientId = user.getId();
        log.info("Get therapy by id : {} for patient with id : {}", therapyId, patientId);
        return therapyFacade.getTherapyForPatient(therapyId, patientId);
    }

    @PreAuthorize(AUTHORITY_DOCTOR)
    @PostMapping("/add")
    public ResponseTherapyDTO saveTherapy(@RequestBody RequestTherapyDTO requestTherapyDTO) {
        log.info("Add new therapy with description : {}, and patient id : {}", requestTherapyDTO.getDescription(), requestTherapyDTO.getPatientId());
        User doctor = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return therapyFacade.saveTherapy(requestTherapyDTO, doctor.getId());
    }
}
