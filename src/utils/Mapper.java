package utils;

import data.models.Resident;
import dtos.requests.RegisterResidentRequest;
import dtos.responses.RegisterResidentResponse;

public class Mapper {

    public static Resident mapToResident(RegisterResidentRequest request) {
        Resident resident = new Resident();
        resident.setFullName(request.getFullName());
        resident.setPhoneNumber(request.getPhoneNumber());
        resident.setEmail(request.getEmail());
        return resident;
    }

    public static RegisterResidentResponse mapToResponse(Resident resident) {
        RegisterResidentResponse response = new RegisterResidentResponse();
        response.setResidentId(resident.getId());
        response.setFullName(resident.getFullName());
        response.setMessage("Resident registered successfully");
        return response;
    }
}