package services;

import dtos.responses.FindVisitorResponse;

public interface VisitorService {
    FindVisitorResponse findById(int id);
}