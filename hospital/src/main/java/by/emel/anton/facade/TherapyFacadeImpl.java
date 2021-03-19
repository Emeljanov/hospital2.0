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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TherapyFacadeImpl implements TherapyFacade {

    private final TherapyService therapyService;
    private final UserService userService;
    private final PatientCardService patientCardService;
    private final Converter<Therapy, ResponseTherapyDTO> therapyConverter;


    @Override
    public ResponseTherapyDTO save(RequestTherapyDTO requestTherapyDTO, int doctorId) {

        User patient = userService.findById(requestTherapyDTO.getPatientId());
        PatientCard patientCard = patientCardService.findByPatientId(patient.getId());
        User doctor = userService.findById(doctorId);

        Therapy therapy = Therapy.builder()
                .description(requestTherapyDTO.getDescription())
                .startDate(requestTherapyDTO.getStartDate())
                .endDate(requestTherapyDTO.getEndDate())
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
    public ResponseTherapyDTO findByIdForCardId(int therapyId, int cardId) {
        return therapyConverter.convert(therapyService.findByIdForCardId(therapyId, cardId));
    }

    @Override
    public List<ResponseTherapyDTO> findAllByCardId(int patientId) {
        return therapyConverter.convertAll(therapyService.findAllForCardId(patientId));
    }
}
