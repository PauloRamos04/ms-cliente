package saudeconectada.fatec.domain.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "patients") // Nome da tabela no banco de dados
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    @Size(min = 11, max = 11)
    private String cpf;
    private String email;
    private String phone;
    //Tabela Adress
    private String address;
    private String gender;
    private LocalDate birthDate;
    //Tabela Children
    private String children;
    //Tabela SusDocument
    private String susDocument;
    //Tabela Deficiency - Enum
    private String deficiency;
    private String photo;
    //Tratar Password com auth
    @NotNull
    @Size(min = 8, max = 20)
    private String password;
}
