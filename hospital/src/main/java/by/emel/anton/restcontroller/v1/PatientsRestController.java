package by.emel.anton.restcontroller.v1;

import by.emel.anton.api.ResponsePatientCardDTO;
import by.emel.anton.api.ResponseTherapyDTO;
import by.emel.anton.entity.User;
import by.emel.anton.facade.PatientCardFacade;
import by.emel.anton.facade.TherapyFacade;
import by.emel.anton.security.exception.JwtAuthenticationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

import static by.emel.anton.restcontroller.v1.Constants.AUTHORITY_PATIENT;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/patients")
public class PatientsRestController {

    private final TherapyFacade therapyFacade;
    private final PatientCardFacade patientCardFacade;

    @PreAuthorize(AUTHORITY_PATIENT)
    @GetMapping("{patientId}/therapies")
    public ResponseEntity<List<ResponseTherapyDTO>> findAllTherapies(@PathVariable(name = "patientId") int patientId, Principal principal) {
        User patient = (User) principal;
        int patientPrincipalId = patient.getId();
        checkParamForEquality(patientId, patientPrincipalId);

        return ResponseEntity.ok(therapyFacade.getAllTherapiesForPatient(patientPrincipalId));
    }

    @PreAuthorize(AUTHORITY_PATIENT)
    @GetMapping("{patientId}/therapies/{therapyId}")
    public ResponseTherapyDTO findTherapy(@PathVariable(name = "patientId") int patientId, @PathVariable(name = "therapyId") int therapyId, Principal principal) {

        User patient = (User) principal;
        int patientPrincipalId = patient.getId();
        checkParamForEquality(patientId, patientPrincipalId);

        return therapyFacade.getTherapyForPatient(therapyId, patientPrincipalId);
    }

    @PreAuthorize(AUTHORITY_PATIENT)
    @GetMapping("{patientId}/card")
    public ResponsePatientCardDTO findPatientCard(@PathVariable(name = "patientId") int patientId, Principal principal) {
        User patient = (User) principal;
        int patientPrincipalId = patient.getId();
        checkParamForEquality(patientId, patientPrincipalId);

        return patientCardFacade.getCardForPatient(patientPrincipalId);
    }

    private void checkParamForEquality(int id1, int id2) {
        if (id1 != id2)
            throw new JwtAuthenticationException("Patient id from path doesn't equal patient id from User Details");
    }


}
