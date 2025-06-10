package services;

import data.models.AccessCode;
import data.models.Resident;
import data.models.Security;
import data.models.Visitor;
import data.repository.AccessCodeRepository;
import data.repository.ResidentRepository;
import data.repository.SecurityRepository;
import data.repository.VisitorRepository;
import dtos.requests.GenerateAccessCodeRequest;
import dtos.requests.VerifyAccessCodeRequest;
import dtos.responses.GenerateAccessCodeResponse;
import dtos.responses.VerifyAccessCodeResponse;
import utils.Mapper;

import java.security.SecureRandom;

public class AccessCodeServiceImpl implements AccessCodeService {
    private final ResidentRepository residentRepository;
    private final AccessCodeRepository accessCodeRepository;
    private final VisitorRepository visitorRepository;
    private final SecurityRepository securityRepository;
    private final SecureRandom secureRandom;

    public AccessCodeServiceImpl(ResidentRepository residentRepository, AccessCodeRepository accessCodeRepository,
                                 VisitorRepository visitorRepository, SecurityRepository securityRepository) {
        this.residentRepository = residentRepository;
        this.accessCodeRepository = accessCodeRepository;
        this.visitorRepository = visitorRepository;
        this.securityRepository = securityRepository;
        this.secureRandom = new SecureRandom();
    }

    private String generateAccessCode() {
        int token = 100000 + secureRandom.nextInt(900000);
        return String.valueOf(token);
    }

    @Override
    public GenerateAccessCodeResponse generateAccessCodeForVisitor(GenerateAccessCodeRequest request) {
        if (request.getResidentId() <= 0) {
            throw new IllegalArgumentException("Invalid resident ID");
        }
        if (request.getVisitorFullName() == null || request.getVisitorFullName().isEmpty()) {
            throw new IllegalArgumentException("Visitor full name is required");
        }

        Resident resident = residentRepository.findById(request.getResidentId())
                .orElseThrow(() -> new IllegalArgumentException("Resident with ID " + request.getResidentId() + " not found"));

        Visitor visitor = Mapper.mapToVisitor(request);
        Visitor savedVisitor = visitorRepository.save(visitor);

        String accessCode = generateAccessCode();
        AccessCode newAccessCode = new AccessCode(accessCode, resident);
        newAccessCode.setVisitor(savedVisitor);
        accessCodeRepository.save(newAccessCode);

        GenerateAccessCodeResponse response = Mapper.createGenerateAccessCodeResponse();
        response.setAccessCode(accessCode);
        response.setMessage("Access code generated successfully for visitor " + savedVisitor.getFullName());
        return response;
    }

    @Override
    public VerifyAccessCodeResponse verifyAccessCode(VerifyAccessCodeRequest request) {
        if (request.getSecurityId() <= 0) {
            throw new IllegalArgumentException("Invalid security ID");
        }

        Security security = securityRepository.findById(request.getSecurityId())
                .orElseThrow(() -> new IllegalArgumentException("Security with ID " + request.getSecurityId() + " not found"));

        if (request.getAccessCode() == null || request.getAccessCode().isEmpty()) {
            throw new IllegalArgumentException("Access code is required");
        }

        AccessCode foundCode = accessCodeRepository.findByCode(request.getAccessCode()).orElse(null);
        if (foundCode == null) {
            throw new IllegalArgumentException("Access code not found");
        }

        VerifyAccessCodeResponse response = Mapper.createVerifyAccessCodeResponse(foundCode.getResident());

        if (foundCode.isExpired()) {
            throw new IllegalArgumentException("Access code has expired");
        }
        if (foundCode.isUsed()) {
            throw new IllegalArgumentException("Access code has already been used");
        }

        foundCode.setUsed(true);
        response.setMessage("Access granted to visitor " + foundCode.getVisitor().getFullName() + " by security " + security.getFullName());
        return response;
    }
}