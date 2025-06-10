package services;

import data.models.Resident;
import data.models.Security;
import data.repository.*;
import dtos.requests.GenerateAccessCodeRequest;
import dtos.requests.RegisterResidentRequest;
import dtos.requests.RegisterSecurityRequest;
import dtos.requests.VerifyAccessCodeRequest;
import dtos.responses.GenerateAccessCodeResponse;
import dtos.responses.VerifyAccessCodeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccessCodeServiceImplTest {
    private AccessCodeService accessCodeService;
    private ResidentRepository residentRepository;
    private SecurityRepository securityRepository;
    private VisitorRepository visitorRepository;
    private AccessCodeRepository accessCodeRepository;
    private ResidentServices residentServices;
    private SecurityServices securityService;

    @BeforeEach
    public void setUp() {
        residentRepository = new Residents();
        securityRepository = new Securities();
        visitorRepository = new Visitors();
        accessCodeRepository = new AccessCodes();
        accessCodeService = new AccessCodeServiceImpl(residentRepository, accessCodeRepository, visitorRepository, securityRepository);
        residentServices = new ResidentServicesImpl(residentRepository);
        securityService = new SecurityServicesImpl(securityRepository);

        residentRepository.deleteAll();
        securityRepository.deleteAll();
        visitorRepository.deleteAll();
        accessCodeRepository.deleteAll();
    }

    @Test
    public void testGenerateAccessCode_Success() {
        // Register a resident
        RegisterResidentRequest residentRequest = new RegisterResidentRequest();
        residentRequest.setFullName("Yusuf Usman");
        residentRequest.setPhoneNumber("08169025216");
        residentRequest.setEmail("yusuf.usman@example.com");
        residentRequest.setPassword("password123");
        residentServices.register(residentRequest);

        // Generate access code
        GenerateAccessCodeRequest request = new GenerateAccessCodeRequest();
        request.setResidentId(1);
        request.setVisitorFullName("John Doe");
        request.setVisitorPhoneNumber("09012345678");
        request.setVisitorEmail("john.doe@example.com");

        GenerateAccessCodeResponse response = accessCodeService.generateAccessCodeForVisitor(request);

        assertNotNull(response.getAccessCode());
        assertEquals(6, response.getAccessCode().length()); // Assuming 6-digit code
        assertEquals("Access code generated successfully for visitor John Doe", response.getMessage());
    }

    @Test
    public void testGenerateAccessCode_Failure_InvalidResidentId() {
        GenerateAccessCodeRequest request = new GenerateAccessCodeRequest();
        request.setResidentId(0);
        request.setVisitorFullName("John Doe");
        request.setVisitorPhoneNumber("09012345678");
        request.setVisitorEmail("john.doe@example.com");

        assertThrows(IllegalArgumentException.class, () -> accessCodeService.generateAccessCodeForVisitor(request), "Invalid resident ID");
    }

    @Test
    public void testGenerateAccessCode_Failure_ResidentNotFound() {
        GenerateAccessCodeRequest request = new GenerateAccessCodeRequest();
        request.setResidentId(1);
        request.setVisitorFullName("John Doe");
        request.setVisitorPhoneNumber("09012345678");
        request.setVisitorEmail("john.doe@example.com");

        assertThrows(IllegalArgumentException.class, () -> accessCodeService.generateAccessCodeForVisitor(request), "Resident with ID 1 not found");
    }

    @Test
    public void testGenerateAccessCode_Failure_MissingVisitorName() {
        RegisterResidentRequest residentRequest = new RegisterResidentRequest();
        residentRequest.setFullName("Yusuf Usman");
        residentRequest.setPhoneNumber("08169025216");
        residentRequest.setEmail("yusuf.usman@example.com");
        residentRequest.setPassword("password123");
        residentServices.register(residentRequest);

        GenerateAccessCodeRequest request = new GenerateAccessCodeRequest();
        request.setResidentId(1);
        request.setVisitorFullName("");
        request.setVisitorPhoneNumber("09012345678");
        request.setVisitorEmail("john.doe@example.com");

        assertThrows(IllegalArgumentException.class, () -> accessCodeService.generateAccessCodeForVisitor(request), "Visitor full name is required");
    }

    @Test
    public void testVerifyAccessCode_Success() {
        // Register a resident
        RegisterResidentRequest residentRequest = new RegisterResidentRequest();
        residentRequest.setFullName("Yusuf Usman");
        residentRequest.setPhoneNumber("08169025216");
        residentRequest.setEmail("yusuf.usman@example.com");
        residentRequest.setPassword("password123");
        residentServices.register(residentRequest);

        // Register a security user
        RegisterSecurityRequest securityRequest = new RegisterSecurityRequest();
        securityRequest.setFullName("Jane Doe");
        securityRequest.setPhoneNumber("09012345678");
        securityRequest.setEmail("jane.doe@example.com");
        securityRequest.setPassword("password123");
        securityService.register(securityRequest);

        // Generate an access code
        GenerateAccessCodeRequest generateRequest = new GenerateAccessCodeRequest();
        generateRequest.setResidentId(1);
        generateRequest.setVisitorFullName("John Doe");
        generateRequest.setVisitorPhoneNumber("09012345678");
        generateRequest.setVisitorEmail("john.doe@example.com");
        GenerateAccessCodeResponse generateResponse = accessCodeService.generateAccessCodeForVisitor(generateRequest);

        VerifyAccessCodeRequest verifyRequest = new VerifyAccessCodeRequest();
        verifyRequest.setSecurityId(1);
        verifyRequest.setAccessCode(generateResponse.getAccessCode());

        VerifyAccessCodeResponse verifyResponse = accessCodeService.verifyAccessCode(verifyRequest);

        assertEquals(1, verifyResponse.getResidentId());
        assertEquals("Access granted to visitor John Doe by security Jane Doe", verifyResponse.getMessage());
    }

    @Test
    public void testVerifyAccessCode_Failure_InvalidSecurityId() {
        VerifyAccessCodeRequest verifyRequest = new VerifyAccessCodeRequest();
        verifyRequest.setSecurityId(0);
        verifyRequest.setAccessCode("123456");

        assertThrows(IllegalArgumentException.class, () -> accessCodeService.verifyAccessCode(verifyRequest), "Invalid security ID");
    }

    @Test
    public void testVerifyAccessCode_Failure_SecurityNotFound() {
        VerifyAccessCodeRequest verifyRequest = new VerifyAccessCodeRequest();
        verifyRequest.setSecurityId(1);
        verifyRequest.setAccessCode("123456");

        assertThrows(IllegalArgumentException.class, () -> accessCodeService.verifyAccessCode(verifyRequest), "Security with ID 1 not found");
    }

    @Test
    public void testVerifyAccessCode_Failure_AccessCodeNotFound() {
        RegisterSecurityRequest securityRequest = new RegisterSecurityRequest();
        securityRequest.setFullName("Jane Doe");
        securityRequest.setPhoneNumber("09012345678");
        securityRequest.setEmail("jane.doe@example.com");
        securityRequest.setPassword("password123");
        securityService.register(securityRequest);

        VerifyAccessCodeRequest verifyRequest = new VerifyAccessCodeRequest();
        verifyRequest.setSecurityId(1);
        verifyRequest.setAccessCode("123456");

        assertThrows(IllegalArgumentException.class, () -> accessCodeService.verifyAccessCode(verifyRequest), "Access code not found");
    }

    @Test
    public void testVerifyAccessCode_Failure_AccessCodeUsed() {
        RegisterResidentRequest residentRequest = new RegisterResidentRequest();
        residentRequest.setFullName("Yusuf Usman");
        residentRequest.setPhoneNumber("08169025216");
        residentRequest.setEmail("yusuf.usman@example.com");
        residentRequest.setPassword("password123");
        residentServices.register(residentRequest);

        RegisterSecurityRequest securityRequest = new RegisterSecurityRequest();
        securityRequest.setFullName("Jane Doe");
        securityRequest.setPhoneNumber("09012345678");
        securityRequest.setEmail("jane.doe@example.com");
        securityRequest.setPassword("password123");
        securityService.register(securityRequest);

        GenerateAccessCodeRequest generateRequest = new GenerateAccessCodeRequest();
        generateRequest.setResidentId(1);
        generateRequest.setVisitorFullName("John Doe");
        generateRequest.setVisitorPhoneNumber("09012345678");
        generateRequest.setVisitorEmail("john.doe@example.com");
        GenerateAccessCodeResponse generateResponse = accessCodeService.generateAccessCodeForVisitor(generateRequest);

        VerifyAccessCodeRequest verifyRequest = new VerifyAccessCodeRequest();
        verifyRequest.setSecurityId(1);
        verifyRequest.setAccessCode(generateResponse.getAccessCode());
        accessCodeService.verifyAccessCode(verifyRequest);

        assertThrows(IllegalArgumentException.class, () -> accessCodeService.verifyAccessCode(verifyRequest), "Access code has already been used");
    }
}