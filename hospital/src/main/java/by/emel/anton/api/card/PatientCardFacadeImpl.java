package by.emel.anton.api.card;

import by.emel.anton.api.converter.Converter;
import by.emel.anton.model.entity.card.PatientCard;
import by.emel.anton.model.entity.user.User;
import by.emel.anton.service.PatientCardService;
import by.emel.anton.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PatientCardFacadeImpl implements PatientCardFacade {

    private PatientCardService patientCardService;
    private UserService userService;
    private Converter<PatientCard, ResponsePatientCard> converter;

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
    public ResponsePatientCard getPatientCard(int cardId) {

        return converter.convert(patientCardService.getPatientCardById(cardId));
    }
}
