package by.emel.anton.facade;

import by.emel.anton.api.ResponsePatientCardDTO;
import by.emel.anton.facade.converter.Converter;
import by.emel.anton.entity.PatientCard;
import by.emel.anton.entity.User;
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
    private Converter<PatientCard, ResponsePatientCardDTO> converter;

    @Override
    public void createPatientCard(int patientId) {

        User patient = userService.getUserById(patientId);
        PatientCard patientCard = PatientCard
                .builder()
                .patient(patient)
                .build();

        patientCardService.savePatientCard(patientCard);
    }

    @Override
    public ResponsePatientCardDTO getPatientCard(int cardId) {

        return converter.convert(patientCardService.getPatientCardById(cardId));
    }

    @Override
    public List<ResponsePatientCardDTO> getAllCards() {
        return converter.convertAll(patientCardService.getAllCards());
    }

    @Override
    public ResponsePatientCardDTO getPatientCardForPatient(int cardId, int patientId) {
        return converter.convert(patientCardService.getPatientCardForPatient(cardId, patientId));
    }

    @Override
    public ResponsePatientCardDTO getCardForPatient(int patientId) {
        return converter.convert(patientCardService.getPatientCardByPatientId(patientId));
    }
}
