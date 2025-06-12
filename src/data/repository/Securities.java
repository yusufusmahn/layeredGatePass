package data.repository;

import data.models.Security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Securities implements SecurityRepository {
    private int counter = 0;
    private static List<Security> securities = new ArrayList<>();

    @Override
    public Security save(Security security) {
        if (security.getId() == 0) {
            security.setId(++counter);
            securities.add(security);
        } else {
            for (int i = 0; i < securities.size(); i++) {
                if (securities.get(i).equals(security)) {
                    securities.set(i, security);
                    return security;
                }
            }
            securities.add(security);
        }
        return security;
    }


    public void delete(int id) {
        Security securityToRemove = new Security();
        securityToRemove.setId(id);
        securities.removeIf(security -> security.equals(securityToRemove));
    }

    @Override
    public void deleteById(int id) {
        delete(id);
    }

    @Override
    public Optional<Security> findById(int id) {
        for (Security security : securities) {
            if (security.getId() == id) {
                return Optional.of(security);
            }
        }
        return Optional.empty();
    }


    public List<Security> findAllByFullName(String fullName) {
        List<Security> result = new ArrayList<>();
        for (Security security : securities) {
            if (security.getFullName() != null && security.getFullName().equals(fullName)) {
                result.add(security);
            }
        }
        return result;
    }

    @Override
    public long count() {
        return securities.size();
    }

    @Override
    public List<Security> findAll() {
        return new ArrayList<>(securities);
    }

    @Override
    public void deleteAll() {
        securities.clear();
        counter = 0;
    }

    @Override
    public Optional<Security> findByEmail(String email) {
        for (Security security : securities) {
            if (security.getEmail().equalsIgnoreCase(email)) {
                return Optional.of(security);
            }
        }
        return Optional.empty();
    }
}