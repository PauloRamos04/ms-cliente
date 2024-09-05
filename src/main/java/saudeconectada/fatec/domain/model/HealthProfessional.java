    package saudeconectada.fatec.domain.model;

    import jakarta.persistence.*;
    import lombok.Data;
    import saudeconectada.fatec.domain.enums.Gender;
    import saudeconectada.fatec.domain.enums.ProfessionalType;

    import java.time.LocalDate;

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
    }