package by.emel.anton.repository.implementation.jparepository;

import by.emel.anton.model.entity.therapy.Therapy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface TherapyJpaRepository extends JpaRepository<Therapy,Integer> {
}
