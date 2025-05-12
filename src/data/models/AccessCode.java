package data.models;

import java.time.LocalDateTime;

public class AccessCode {
    private int id;
    private String code;
    private Resident resident;
    private Visitor visitor;
    private LocalDateTime creationDate = LocalDateTime.now();
    private boolean isUsed;
    private LocalDateTime startDateAndTime;
}
