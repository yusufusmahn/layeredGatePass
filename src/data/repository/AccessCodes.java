package data.repository;

import data.models.AccessCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccessCodes implements AccessCodeRepository {
    private List<AccessCode> accessCodes = new ArrayList<>();

    @Override
    public AccessCode save(AccessCode accessCode) {
        accessCodes.add(accessCode);
        return accessCode;
    }

    @Override
    public List<AccessCode> findAll() {
        return new ArrayList<>(accessCodes);
    }

    @Override
    public Optional<AccessCode> findByCode(String code) {
        for (AccessCode accessCode : accessCodes) {
            if (accessCode.getCode().equals(code)) {
                return Optional.of(accessCode);
            }
        }
        return Optional.empty();
    }

    @Override
    public long count() {
        return accessCodes.size();
    }

    @Override
    public void deleteAll() {
        accessCodes.clear();
    }
}