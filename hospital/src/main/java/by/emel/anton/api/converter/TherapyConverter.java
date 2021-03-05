package by.emel.anton.api.converter;

import by.emel.anton.api.therapy.ResponseTherapyDTO;
import by.emel.anton.model.entity.therapy.Therapy;
import org.springframework.stereotype.Component;

@Component
public class TherapyConverter implements Converter<Therapy, ResponseTherapyDTO> {
    @Override
    public ResponseTherapyDTO convert(Therapy from) {
        return ResponseTherapyDTO.builder()
                .id(from.getId())
                .description(from.getDescription())
                .startDate(from.getStartDate())
                .endDate(from.getEndDate())
                .patientId(from.getPatient().getId())
                .doctorId(from.getDoctor().getId())
                .build();
    }
}
