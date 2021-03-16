package by.emel.anton.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class PatientCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id", nullable = false, unique = true)
    private User patient;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "card", cascade = CascadeType.ALL)
    private List<Therapy> therapies = new ArrayList<>();

}
