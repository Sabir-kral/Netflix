package az.developia.demo.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "abone")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AboneEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;

    private Long premium;
    private Long basic;
    private Long standard;

    @OneToOne(mappedBy = "abone")
    private VideoEntity video;
}
