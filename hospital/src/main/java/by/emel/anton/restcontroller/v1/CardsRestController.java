package by.emel.anton.restcontroller.v1;

import by.emel.anton.api.v1.RequestPatientCardDTO;
import by.emel.anton.api.v1.ResponsePatientCardDTO;
import by.emel.anton.facade.PatientCardFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        return ResponseEntity.ok(patientCardFacade.findAll());
    }

    @PreAuthorize(AUTHORITY_DOCTOR)
    @GetMapping("{id}")
    public ResponsePatientCardDTO findCardById(@PathVariable(name = "id") int cardId) {
        return patientCardFacade.findById(cardId);
    }

    @PreAuthorize(AUTHORITY_DOCTOR)
    @GetMapping("patients/{id}")
    public ResponsePatientCardDTO findCardByPatientId(@PathVariable(name = "id") int patientId) {
        return patientCardFacade.findByPatientId(patientId);
    }

    @PreAuthorize(AUTHORITY_DOCTOR)
    @PostMapping
    public ResponsePatientCardDTO saveCard(@RequestBody @Valid RequestPatientCardDTO requestPatientCardDTO) {
        return patientCardFacade.createForPatientId(requestPatientCardDTO.getPatientId());
    }
}
