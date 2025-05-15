package services;

import dtos.requests.RegisterResidentRequest;
import dtos.responses.RegisterResidentResponse;
import data.models.Resident;
import data.repository.ResidentRepository;

import utils.Mapper;

public class ResidentServicesImpl implements ResidentServices {
    private ResidentRepository residentRepository;

        public ResidentServicesImpl(ResidentRepository residentRepository) {
            this.residentRepository = residentRepository;
        }




    @Override
        public RegisterResidentResponse register(RegisterResidentRequest registerResidentRequest) {
            if (registerResidentRequest.getFullName() == null || registerResidentRequest.getFullName().isEmpty()) {
                throw new IllegalArgumentException("Full name is required");
            }
            if (registerResidentRequest.getEmail() == null || !registerResidentRequest.getEmail().contains("@")) {
                throw new IllegalArgumentException("Valid email is required");
            }

            Resident resident = Mapper.mapToResident(registerResidentRequest);

            Resident savedResident = residentRepository.save(resident);

            return Mapper.mapToResponse(savedResident);
        }
    }

