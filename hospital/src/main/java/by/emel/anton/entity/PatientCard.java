package by.emel.anton.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "patient_id", nullable = false, unique = true)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    private User patient;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Therapy> therapies = new ArrayList<>();

}
