package saudeconectada.fatec.infra.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saudeconectada.fatec.domain.model.Patient;
import saudeconectada.fatec.domain.model.HealthProfessional;
import saudeconectada.fatec.domain.model.Verifiable;
import saudeconectada.fatec.repository.PatientRepository;
import saudeconectada.fatec.repository.HealthProfessionalRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class VerifyService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private HealthProfessionalRepository healthProfessionalRepository;

    public String verifyUser(UUID token, Class<? extends Verifiable> userType) {
        Optional<? extends Verifiable> userOptional;

        if (userType.equals(Patient.class)) {
            userOptional = patientRepository.findByVerificationToken(token);
        } else if (userType.equals(HealthProfessional.class)) {
            userOptional = healthProfessionalRepository.findByVerificationToken(token);
        } else {
            throw new IllegalArgumentException("Tipo de usuário desconhecido.");
        }

        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("Token inválido.");
        }

        Verifiable user = userOptional.get();
        if (user instanceof Patient) {
            Patient patient = (Patient) user;
            patient.setVerificationToken(null);
            patient.setVerified(true);
            patientRepository.save(patient);
        } else if (user instanceof HealthProfessional) {
            HealthProfessional healthProfessional = (HealthProfessional) user;
            healthProfessional.setVerificationToken(null);
            healthProfessional.setVerified(true);
            healthProfessionalRepository.save(healthProfessional);
        }

        return "Usuário autenticado";
    }
}
