package by.emel.anton.model.entity.therapy;

import by.emel.anton.model.entity.card.PatientCard;
import by.emel.anton.model.entity.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class Therapy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String description;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_card_id", nullable = false)
    private PatientCard card;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;

}
