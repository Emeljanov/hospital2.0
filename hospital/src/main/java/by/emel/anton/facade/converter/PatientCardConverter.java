package by.emel.anton.facade.converter;

import by.emel.anton.api.v1.ResponsePatientCardDTO;
import by.emel.anton.api.v1.ResponseTherapyDTO;
import by.emel.anton.entity.PatientCard;
import by.emel.anton.entity.Therapy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PatientCardConverter implements Converter<PatientCard, ResponsePatientCardDTO> {

    private Converter<Therapy, ResponseTherapyDTO> converter;

    @Override
    public ResponsePatientCardDTO convert(PatientCard from) {
        return ResponsePatientCardDTO.builder()
                .id(from.getId())
                .patientId(from.getPatient().getId())
                .therapyDTOList(converter.convertAll(from.getTherapies()))
                .build();
    }
}
