    package saudeconectada.fatec.domain.model;

    import jakarta.persistence.*;
    import jakarta.validation.constraints.Email;
    import jakarta.validation.constraints.NotNull;
    import jakarta.validation.constraints.Size;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.time.LocalDate;
    import java.util.HashSet;
    import java.util.Set;

    @Entity
    @Data
    @Table(name = "health_professional")
    public class HealthProfessional {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;
        private String password;
        private String cpf;
        private String email;
        private String healthUnitNumber;
        private String phone;
        private String address;
        private LocalDate birthDate;
        private Gender gender;
        private ProfessionalType professionalType;

        @OneToMany(mappedBy = "healthProfessional", cascade = CascadeType.ALL, orphanRemoval = true)
        private Set<LoginHistory> loginHistory = new HashSet<>();

        public void addLoginHistory(LoginHistory history) {
            loginHistory.add(history);
            history.setHealthProfessional(this);
        }

        public void removeLoginHistory(LoginHistory history) {
            loginHistory.remove(history);
            history.setHealthProfessional(null);
        }
    }