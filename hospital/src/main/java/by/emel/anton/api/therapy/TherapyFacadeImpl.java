package by.emel.anton.api.therapy;

import by.emel.anton.api.converter.Converter;
import by.emel.anton.model.entity.card.PatientCard;
import by.emel.anton.model.entity.therapy.Therapy;
import by.emel.anton.model.entity.user.User;
import by.emel.anton.service.PatientCardService;
import by.emel.anton.service.TherapyService;
import by.emel.anton.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TherapyFacadeImpl implements TherapyFacade {

    private TherapyService therapyService;
    private UserService userService;
    private PatientCardService patientCardService;
    private Converter<Therapy, ResponseTherapyDTO> converter;


    @Override
    public void saveTherapy(RequestTherapyDTO requestTherapyDTO, int patientCardId, int doctorId) {

        PatientCard patientCard = patientCardService.getPatientCardById(patientCardId);
        User patient = userService.getUserById(patientCard.getPatient().getId());
        User doctor = userService.getUserById(doctorId);

        Therapy therapy = Therapy.builder()
                .description(requestTherapyDTO.getDescription())
                .startDate(requestTherapyDTO.getStartDate())
                .endDate(requestTherapyDTO.getEndDate())
                .patient(patient)
                .doctor(doctor)
                .card(patientCard)
                .build();

        therapyService.saveTherapy(therapy);
    }

    @Override
    public ResponseTherapyDTO getTherapy(int id) {
        return converter.convert(therapyService.getTherapyById(id));
    }
}
