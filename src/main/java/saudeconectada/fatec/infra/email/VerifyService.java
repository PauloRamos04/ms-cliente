package saudeconectada.fatec.infra.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saudeconectada.fatec.domain.model.Patient;
import saudeconectada.fatec.repository.PatientRepository;

import java.util.UUID;

@Service
public class VerifyService {

    @Autowired
    private PatientRepository patientRepository;

    public String verifyUser(UUID token){
        Patient patient = patientRepository.findByVerificationToken(token);

        if(patient == null){
            throw new IllegalArgumentException("Token Invalido.");
        }

        patient.setVerificationToken(null);
        patient.setVerified(true);
        patientRepository.save(patient);

        return "Paciente autenticado";
    }
}
