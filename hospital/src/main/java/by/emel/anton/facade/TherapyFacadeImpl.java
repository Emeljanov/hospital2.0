package by.emel.anton.facade;

import by.emel.anton.api.RequestTherapyDTO;
import by.emel.anton.api.ResponseTherapyDTO;
import by.emel.anton.facade.converter.Converter;
import by.emel.anton.entity.PatientCard;
import by.emel.anton.entity.Therapy;
import by.emel.anton.entity.User;
import by.emel.anton.service.PatientCardService;
import by.emel.anton.service.TherapyService;
import by.emel.anton.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class TherapyFacadeImpl implements TherapyFacade {

    private TherapyService therapyService;
    private UserService userService;
    private PatientCardService patientCardService;
    private Converter<Therapy, ResponseTherapyDTO> converter;


    @Override
    public ResponseTherapyDTO saveTherapy(RequestTherapyDTO requestTherapyDTO, int doctorId) {

        User patient = userService.getUserById(requestTherapyDTO.getPatientId());
        PatientCard patientCard = patientCardService.getPatientCardByPatientId(patient.getId());
        User doctor = userService.getUserById(doctorId);

        Therapy therapy = Therapy.builder()
                .description(requestTherapyDTO.getDescription())
                .startDate(requestTherapyDTO.getStartDate())
                .endDate(requestTherapyDTO.getEndDate())
                .patient(patient)
                .doctor(doctor)
                .card(patientCard)
                .build();

       return converter.convert(therapyService.saveTherapy(therapy));
    }

    @Override
    public ResponseTherapyDTO getTherapy(int id) {
        return converter.convert(therapyService.getTherapyById(id));
    }

    @Override
    public List<ResponseTherapyDTO> getAllTherapies() {
        return converter.convertAll(therapyService.getAllTherapies());
    }

    @Override
    public ResponseTherapyDTO getTherapyForPatient(int therapyId, int patientId) {
        return converter.convert(therapyService.getTherapyByIdForPatient(therapyId,patientId));
    }

    @Override
    public List<ResponseTherapyDTO> getAllTherapiesForPatient(int patientId) {
        return converter.convertAll(therapyService.getAllTherapiesForPatient(patientId));
    }
}
