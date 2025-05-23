package data.repository;

import data.models.AccessCode;

import java.util.List;
import java.util.Optional;

public interface AccessCodeRepository {
    AccessCode save(AccessCode accessCode);
    List<AccessCode> findAll();
    Optional<AccessCode> findByCode(String code);
    long count();
    void deleteAll();
}