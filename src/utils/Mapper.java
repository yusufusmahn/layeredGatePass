package utils;

import data.models.Resident;
import data.models.Security;
import data.models.Visitor;
import dtos.requests.GenerateAccessCodeRequest;
import dtos.requests.RegisterResidentRequest;
import dtos.requests.RegisterSecurityRequest;
import dtos.responses.*;

import java.util.ArrayList;
import java.util.List;

public class Mapper {
    public static Resident mapToResident(RegisterResidentRequest request) {
        Resident resident = new Resident();
        resident.setFullName(request.getFullName());
        resident.setPhoneNumber(request.getPhoneNumber());
        resident.setEmail(request.getEmail());
        return resident;
    }

    public static FindResidentResponse mapToFindResponse(Resident resident) {
        FindResidentResponse response = new FindResidentResponse();
        response.setResidentId(resident.getId());
        response.setFullName(resident.getFullName());
        response.setPhoneNumber(resident.getPhoneNumber());
        response.setEmail(resident.getEmail());
        return response;
    }

    public static List<FindResidentResponse> mapToFindResponseList(List<Resident> residents) {
        List<FindResidentResponse> responses = new ArrayList<>();
        for (Resident resident : residents) {
            responses.add(mapToFindResponse(resident));
        }
        return responses;
    }

    public static Visitor mapToVisitor(GenerateAccessCodeRequest request) {
        Visitor visitor = new Visitor();
        visitor.setFullName(request.getVisitorFullName());
        visitor.setPhoneNumber(request.getVisitorPhoneNumber());
        visitor.setEmail(request.getVisitorEmail());
        return visitor;
    }

    public static FindVisitorResponse mapToFindVisitorResponse(Visitor visitor) {
        FindVisitorResponse response = new FindVisitorResponse();
        response.setVisitorId(visitor.getId());
        response.setFullName(visitor.getFullName());
        response.setPhoneNumber(visitor.getPhoneNumber());
        response.setEmail(visitor.getEmail());
        return response;
    }

    public static Security mapToSecurity(RegisterSecurityRequest request) {
        Security security = new Security();
        security.setFullName(request.getFullName());
        security.setPhoneNumber(request.getPhoneNumber());
        security.setEmail(request.getEmail());
        return security;
    }

    public static FindSecurityResponse mapToFindSecurityResponse(Security security) {
        FindSecurityResponse response = new FindSecurityResponse();
        response.setSecurityId(security.getId());
        response.setFullName(security.getFullName());
        response.setPhoneNumber(security.getPhoneNumber());
        response.setEmail(security.getEmail());
        return response;
    }

    public static RegisterResidentResponse createRegisterResidentResponse(Resident resident) {
        RegisterResidentResponse response = new RegisterResidentResponse();
        response.setResidentId(resident.getId());
        response.setFullName(resident.getFullName());
        return response;
    }

    public static LoginResponse createLoginResponse(Resident resident) {
        LoginResponse response = new LoginResponse();
        response.setResidentId(resident.getId());
        return response;
    }

    public static RegisterSecurityResponse createRegisterSecurityResponse(Security security) {
        RegisterSecurityResponse response = new RegisterSecurityResponse();
        response.setSecurityId(security.getId());
        response.setFullName(security.getFullName());
        return response;
    }

    public static LoginSecurityResponse createLoginSecurityResponse(Security security) {
        LoginSecurityResponse response = new LoginSecurityResponse();
        response.setSecurityId(security.getId());
        return response;
    }

    public static GenerateAccessCodeResponse createGenerateAccessCodeResponse() {
        return new GenerateAccessCodeResponse();
    }

    public static VerifyAccessCodeResponse createVerifyAccessCodeResponse(Resident resident) {
        VerifyAccessCodeResponse response = new VerifyAccessCodeResponse();
        response.setResidentId(resident.getId());
        return response;
    }
}