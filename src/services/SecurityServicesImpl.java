package services;

import data.models.Security;
import data.repository.SecurityRepository;
import dtos.requests.LoginSecurityRequest;
import dtos.requests.RegisterSecurityRequest;
import dtos.responses.FindSecurityResponse;
import dtos.responses.LoginSecurityResponse;
import dtos.responses.RegisterSecurityResponse;
import utils.Mapper;

import java.util.Optional;

public class SecurityServicesImpl implements SecurityServices {
    private final SecurityRepository securityRepository;

    public SecurityServicesImpl(SecurityRepository securityRepository) {
        this.securityRepository = securityRepository;
    }

    @Override
    public RegisterSecurityResponse register(RegisterSecurityRequest request) {
        if (request.getFullName() == null || request.getFullName().isEmpty()) {
            throw new IllegalArgumentException("Full name is required");
        }
        if (request.getEmail() == null || !request.getEmail().contains("@")) {
            throw new IllegalArgumentException("Valid email is required");
        }
        if (request.getPassword() == null || request.getPassword().length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }

        Optional<Security> existingSecurity = securityRepository.findByEmail(request.getEmail());
        if (existingSecurity.isPresent()) {
            throw new IllegalArgumentException("Email already exists: " + request.getEmail());
        }

        Security security = Mapper.mapToSecurity(request);
        security.setPassword(request.getPassword());
        Security savedSecurity = securityRepository.save(security);

        RegisterSecurityResponse response = Mapper.createRegisterSecurityResponse(savedSecurity);
        response.setMessage("Security registered successfully");
        return response;
    }

    @Override
    public FindSecurityResponse findById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID: " + id);
        }

        Security foundSecurity = securityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Security with ID " + id + " not found"));

        return Mapper.mapToFindSecurityResponse(foundSecurity);
    }

    @Override
    public LoginSecurityResponse login(LoginSecurityRequest loginRequest) {
        if (loginRequest.getEmail() == null || loginRequest.getEmail().isEmpty() || !loginRequest.getEmail().contains("@")) {
            throw new IllegalArgumentException("Valid email is required");
        }
        if (loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }

        Security foundSecurity = securityRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Security with email " + loginRequest.getEmail() + " not found"));

        if (!foundSecurity.verifyPassword(loginRequest.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        LoginSecurityResponse response = Mapper.createLoginSecurityResponse(foundSecurity);
        response.setMessage("Login successful");
        return response;
    }
}