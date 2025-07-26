package com.pm.patient_service.service;

import com.pm.patient_service.dto.PatientRequestDTO;
import com.pm.patient_service.dto.PatientResponseDTO;
import com.pm.patient_service.mapper.PatientMapper;
import com.pm.patient_service.model.Patient;
import com.pm.patient_service.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patientList = patientRepository.findAll();
        List<PatientResponseDTO> patientResponseDTOS = patientList.stream().map(PatientMapper::toDTO).toList();
        return patientResponseDTOS;
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepository.save(PatientMapper.toEntity(patientRequestDTO));
        return PatientMapper.toDTO(patient);
    }
}
