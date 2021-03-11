package by.emel.anton.restcontroller.v1;

import by.emel.anton.api.v1.ResponsePatientCardDTO;
import by.emel.anton.facade.PatientCardFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static by.emel.anton.restcontroller.v1.Permissions.AUTHORITY_DOCTOR;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/cards")
public class CardsRestController {

    private final PatientCardFacade patientCardFacade;

    @PreAuthorize(AUTHORITY_DOCTOR)
    @GetMapping()
    public ResponseEntity<List<ResponsePatientCardDTO>> findAllCards() {
        log.info("Get all patient cards");
        return ResponseEntity.ok(patientCardFacade.findAll());
    }

    @PreAuthorize(AUTHORITY_DOCTOR)
    @GetMapping("{id}")
    public ResponsePatientCardDTO findCardById(@PathVariable(name = "id") int cardId) {
        log.info("Get card by id : {}", cardId);
        return patientCardFacade.findById(cardId);
    }
    @PreAuthorize((AUTHORITY_DOCTOR))
    @GetMapping("patients/{id}")
    public ResponsePatientCardDTO findCardByPatientId(@PathVariable(name = "id") int patientId) {
        log.info("Get card by patient id : {}",patientId);
        return patientCardFacade.findByPatientId(patientId);
    }
}
