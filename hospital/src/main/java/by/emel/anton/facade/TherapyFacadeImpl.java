package by.emel.anton.facade;

import by.emel.anton.api.v1.RequestTherapyDTO;
import by.emel.anton.api.v1.ResponseTherapyDTO;
import by.emel.anton.entity.PatientCard;
import by.emel.anton.entity.Therapy;
import by.emel.anton.entity.User;
import by.emel.anton.facade.converter.Converter;
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
    private Converter<Therapy, ResponseTherapyDTO> therapyConverter;


    @Override
    public ResponseTherapyDTO save(RequestTherapyDTO requestTherapyDTO, int doctorId) {

        User patient = userService.findById(requestTherapyDTO.getPatientId());
        PatientCard patientCard = patientCardService.findByPatientId(patient.getId());
        User doctor = userService.findById(doctorId);

        Therapy therapy = Therapy.builder()
                .description(requestTherapyDTO.getDescription())
                .startDate(requestTherapyDTO.getStartDate())
                .endDate(requestTherapyDTO.getEndDate())
                .patient(patient)
                .doctor(doctor)
                .card(patientCard)
                .build();

        return therapyConverter.convert(therapyService.save(therapy));
    }

    @Override
    public ResponseTherapyDTO find(int id) {
        return therapyConverter.convert(therapyService.findById(id));
    }

    @Override
    public List<ResponseTherapyDTO> findAll() {
        return therapyConverter.convertAll(therapyService.findAll());
    }

    @Override
    public ResponseTherapyDTO findByIdForPatientId(int therapyId, int patientId) {
        return therapyConverter.convert(therapyService.findByIdForPatientId(therapyId, patientId));
    }

    @Override
    public List<ResponseTherapyDTO> findAllByPatientId(int patientId) {
        return therapyConverter.convertAll(therapyService.getAllForPatientId(patientId));
    }
}
