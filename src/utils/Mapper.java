package utils;

import data.models.Resident;
import dtos.requests.RegisterResidentRequest;
import dtos.responses.FindResidentResponse;

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


}