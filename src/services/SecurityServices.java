package services;

import dtos.requests.LoginSecurityRequest;
import dtos.requests.RegisterSecurityRequest;
import dtos.responses.FindSecurityResponse;
import dtos.responses.LoginSecurityResponse;
import dtos.responses.RegisterSecurityResponse;

public interface SecurityServices {
    RegisterSecurityResponse register(RegisterSecurityRequest request);
    FindSecurityResponse findById(int id);
    LoginSecurityResponse login(LoginSecurityRequest loginRequest);
}