package az.developia.demo.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "managers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "manager_roles",joinColumns=@JoinColumn(name = "manager_id"),inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles;
}
