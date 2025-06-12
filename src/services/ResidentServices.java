package services;

import dtos.requests.LoginRequest;
import dtos.requests.RegisterResidentRequest;
import dtos.responses.FindResidentResponse;
import dtos.responses.LoginResponse;
import dtos.responses.RegisterResidentResponse;

import java.util.List;

public interface ResidentServices {
    RegisterResidentResponse register(RegisterResidentRequest registerResidentRequest);
    FindResidentResponse findById(int id);
    LoginResponse login(LoginRequest loginRequest);
    List<FindResidentResponse> findAll();
}