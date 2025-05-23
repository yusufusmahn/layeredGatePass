package data.models;

import java.time.LocalDateTime;

public class AccessCode {
    private String code;
    private Resident resident;
    private Visitor visitor;
    private boolean used;
    private LocalDateTime creationTime;
    private LocalDateTime expirationTime;

    public AccessCode(String code, Resident resident) {
        this.code = code;
        this.resident = resident;
        this.used = false;
        this.creationTime = LocalDateTime.now();
        this.expirationTime = creationTime.plusMinutes(30);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Resident getResident() {
        return resident;
    }

    public void setResident(Resident resident) {
        this.resident = resident;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expirationTime);
    }
}

