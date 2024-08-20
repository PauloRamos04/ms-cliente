package saudeconectada.fatec.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 100)
    private String street;

    @NotNull
    @Size(min = 1, max = 10)
    private String number;

    @NotNull
    @Size(min = 5, max = 50)
    private String city;

    @NotNull
    @Size(min = 5, max = 50)
    private String state;

    @NotNull
    @Size(min = 8, max = 9)
    private String zipCode;

    private String additionalInfo;
}