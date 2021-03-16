package by.emel.anton.facade.converter;

import by.emel.anton.api.v1.ResponseTherapyDTO;
import by.emel.anton.entity.Therapy;
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
                .doctorId(from.getDoctor().getId())
                .cardId(from.getCard().getId())
                .build();
    }
}
