package by.emel.anton.api.converter;

import by.emel.anton.api.card.ResponsePatientCard;
import by.emel.anton.api.therapy.ResponseTherapyDTO;
import by.emel.anton.model.entity.card.PatientCard;
import by.emel.anton.model.entity.therapy.Therapy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PatientCardConverter implements Converter<PatientCard, ResponsePatientCard> {

    private Converter<Therapy, ResponseTherapyDTO> converter;

    @Override
    public ResponsePatientCard convert(PatientCard from) {
        return ResponsePatientCard.builder()
                .id(from.getId())
                .patientId(from.getPatient().getId())
                .therapyDTOList(converter.convertAll(from.getTherapies()))
                .build();
    }
}
