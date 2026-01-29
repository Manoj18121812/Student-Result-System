package in.manoj.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;   // ✅ DEFAULT VALUE
}
