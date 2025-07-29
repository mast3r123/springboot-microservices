package com.pm.patient_service.controller;

import com.pm.patient_service.dto.PatientRequestDTO;
import com.pm.patient_service.dto.PatientResponseDTO;
import com.pm.patient_service.dto.validators.CreatePatientValidatonGroup;
import com.pm.patient_service.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patient", description = "API for managing patients")
public class PatientController {

    private PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    @Operation(summary = "Get patients")
    public ResponseEntity<List<PatientResponseDTO>> getPatients() {
        List<PatientResponseDTO> patients = patientService.getPatients();
        return ResponseEntity.ok().body(patients);
    }

    @PostMapping
    @Operation(summary = "Create patient")
    public ResponseEntity<PatientResponseDTO> createPatient(@Validated({Default.class, CreatePatientValidatonGroup.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(patientResponseDTO.getId())
                .toUri();
        return ResponseEntity.created(location).body(patientResponseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update patient")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable UUID id, @Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patientResponseDTO = patientService.updatePatient(id, patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete patient")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
