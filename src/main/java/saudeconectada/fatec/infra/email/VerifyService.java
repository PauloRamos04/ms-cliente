package saudeconectada.fatec.infra.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saudeconectada.fatec.domain.model.Patient;
import saudeconectada.fatec.repository.PatientRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class VerifyService {

    @Autowired
    private PatientRepository patientRepository;

    public String verifyUser(UUID token) {

        Optional<Patient> optionalPatient = patientRepository.findByVerificationToken(token);

        if (!optionalPatient.isPresent()) {
            throw new IllegalArgumentException("Token inválido.");
        }

        Patient patient = optionalPatient.get();
        patient.setVerificationToken(null);
        patient.setVerified(true);
        patientRepository.save(patient);

        return "Paciente autenticado";
    }
}
