package services;

import dtos.requests.LoginRequest;
import dtos.requests.RegisterResidentRequest;
import dtos.responses.FindResidentResponse;
import dtos.responses.LoginResponse;
import dtos.responses.RegisterResidentResponse;

public interface ResidentServices {
    RegisterResidentResponse register(RegisterResidentRequest registerResidentRequest);
    FindResidentResponse findById(int id);
    LoginResponse login(LoginRequest loginRequest);
}