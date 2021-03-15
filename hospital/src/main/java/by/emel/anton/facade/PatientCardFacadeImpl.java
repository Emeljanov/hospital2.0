package by.emel.anton.facade;

import by.emel.anton.api.v1.ResponsePatientCardDTO;
import by.emel.anton.entity.PatientCard;
import by.emel.anton.entity.User;
import by.emel.anton.facade.converter.Converter;
import by.emel.anton.service.PatientCardService;
import by.emel.anton.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class PatientCardFacadeImpl implements PatientCardFacade {

    private PatientCardService patientCardService;
    private UserService userService;
    private Converter<PatientCard, ResponsePatientCardDTO> cardConverter;

    @Override
    public ResponsePatientCardDTO createForPatientId(int patientId) {

        User patient = userService.findById(patientId);
        PatientCard patientCard = PatientCard
                .builder()
                .patient(patient)
                .build();

        return cardConverter.convert(patientCardService.save(patientCard));
    }

    @Override
    public ResponsePatientCardDTO findById(int cardId) {

        return cardConverter.convert(patientCardService.findById(cardId));
    }

    @Override
    public List<ResponsePatientCardDTO> findAll() {
        return cardConverter.convertAll(patientCardService.findAll());
    }

    @Override
    public ResponsePatientCardDTO findByPatientId(int patientId) {
        return cardConverter.convert(patientCardService.findByPatientId(patientId));
    }
}
