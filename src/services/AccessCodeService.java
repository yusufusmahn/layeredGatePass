package services;

import dtos.requests.GenerateAccessCodeRequest;
import dtos.requests.VerifyAccessCodeRequest;
import dtos.responses.GenerateAccessCodeResponse;
import dtos.responses.VerifyAccessCodeResponse;

public interface AccessCodeService {
    GenerateAccessCodeResponse generateAccessCodeForVisitor(GenerateAccessCodeRequest request);
    VerifyAccessCodeResponse verifyAccessCode(VerifyAccessCodeRequest request);
}