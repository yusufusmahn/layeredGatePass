package services;

import data.models.Resident;
import data.repository.ResidentRepository;
import dtos.requests.LoginRequest;
import dtos.requests.RegisterResidentRequest;
import dtos.responses.FindResidentResponse;
import dtos.responses.LoginResponse;
import dtos.responses.RegisterResidentResponse;
import utils.Mapper;

import java.util.List;
import java.util.Optional;

public class ResidentServicesImpl implements ResidentServices {
    private final ResidentRepository residentRepository;

    public ResidentServicesImpl(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
    }

    @Override
    public RegisterResidentResponse register(RegisterResidentRequest registerResidentRequest) {
        if (registerResidentRequest.getFullName() == null || registerResidentRequest.getFullName().isEmpty()) {
            throw new IllegalArgumentException("Full name is required");
        }
        if (registerResidentRequest.getEmail() == null || !registerResidentRequest.getEmail().contains("@")) {
            throw new IllegalArgumentException("Valid email is required");
        }
        if (registerResidentRequest.getPassword() == null || registerResidentRequest.getPassword().length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }

        Optional<Resident> existingResident = residentRepository.findByEmail(registerResidentRequest.getEmail());
        if (existingResident.isPresent()) {
            throw new IllegalArgumentException("Email already exists: " + registerResidentRequest.getEmail());
        }

        Resident resident = Mapper.mapToResident(registerResidentRequest);
        resident.setPassword(registerResidentRequest.getPassword());
        Resident savedResident = residentRepository.save(resident);

        RegisterResidentResponse response = Mapper.createRegisterResidentResponse(savedResident);
        response.setMessage("Resident registered successfully");
        return response;
    }

    @Override
    public FindResidentResponse findById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID: " + id);
        }

        Resident foundResident = residentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Resident with ID " + id + " not found"));

        return Mapper.mapToFindResponse(foundResident);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        if (loginRequest.getEmail() == null || loginRequest.getEmail().isEmpty() || !loginRequest.getEmail().contains("@")) {
            throw new IllegalArgumentException("Valid email is required");
        }
        if (loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }

        Resident foundResident = residentRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Resident with email " + loginRequest.getEmail() + " not found"));

        if (!foundResident.verifyPassword(loginRequest.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        LoginResponse response = Mapper.createLoginResponse(foundResident);
        response.setMessage("Login successful");
        return response;
    }

    @Override
    public List<FindResidentResponse> findAll() {
        return Mapper.mapToFindResponseList(residentRepository.findAll());
    }
}