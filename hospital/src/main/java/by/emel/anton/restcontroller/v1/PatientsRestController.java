package by.emel.anton.restcontroller.v1;

import by.emel.anton.api.v1.ResponsePatientCardDTO;
import by.emel.anton.api.v1.ResponseTherapyDTO;
import by.emel.anton.entity.User;
import by.emel.anton.facade.PatientCardFacade;
import by.emel.anton.facade.TherapyFacade;
import by.emel.anton.security.exception.JwtAuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static by.emel.anton.restcontroller.v1.Permissions.AUTHORITY_PATIENT;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/patients")
public class PatientsRestController {

    private final TherapyFacade therapyFacade;
    private final PatientCardFacade patientCardFacade;

    @PreAuthorize(AUTHORITY_PATIENT)
    @GetMapping("{patientId}/therapies")
    public ResponseEntity<List<ResponseTherapyDTO>> findAllTherapies(@PathVariable(name = "patientId") int patientId, Authentication authentication) {

        User patient = (User) authentication.getPrincipal();
        int patientPrincipalId = patient.getId();
        checkParamForEquality(patientId, patientPrincipalId);

        return ResponseEntity.ok(therapyFacade.findAllByPatientId(patientPrincipalId));
    }

    @PreAuthorize(AUTHORITY_PATIENT)
    @GetMapping("{patientId}/therapies/{therapyId}")
    public ResponseTherapyDTO findTherapyById(@PathVariable(name = "patientId") int patientId, @PathVariable(name = "therapyId") int therapyId, Authentication authentication) {

        User patient = (User) authentication.getPrincipal();
        int patientPrincipalId = patient.getId();
        checkParamForEquality(patientId, patientPrincipalId);

        return therapyFacade.findByIdForPatientId(therapyId, patientPrincipalId);
    }

    @PreAuthorize(AUTHORITY_PATIENT)
    @GetMapping("{patientId}/card")
    public ResponsePatientCardDTO findPatientCard(@PathVariable(name = "patientId") int patientId, Authentication authentication) {
        User patient = (User) authentication.getPrincipal();
        int patientPrincipalId = patient.getId();
        checkParamForEquality(patientId, patientPrincipalId);

        return patientCardFacade.findByPatientId(patientPrincipalId);
    }

    private void checkParamForEquality(int id1, int id2) {
        if (id1 != id2)
            throw new JwtAuthenticationException("Patient id from path doesn't equal patient id from User Details");
    }
}
