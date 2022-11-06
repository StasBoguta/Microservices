package com.mentor4you.model.DTO.ComplainDTO;

import com.mentor4you.model.TypeComplain;

import java.time.LocalDateTime;

public class ComplainResponseDTO {
    private String id;
    private int senderId;
    private int mentorId;
    private String message;
    private boolean showStatus;
    private LocalDateTime timestamp;
    private Enum<TypeComplain> typeComplainEnum;

    public ComplainResponseDTO() {
    }

    public ComplainResponseDTO(int senderId,
                               int mentorId,
                               String message,
                               boolean showStatus,
                               LocalDateTime timestamp,
                               Enum<TypeComplain> typeComplainEnum) {
        this.senderId = senderId;
        this.mentorId = mentorId;
        this.message = message;
        this.showStatus = showStatus;
        this.timestamp = timestamp;
        this.typeComplainEnum = typeComplainEnum;
    }

    public String getId() {
        return id;
    }

    public int getSenderId() {
        return senderId;
    }

    public int getMentorId() {
        return mentorId;
    }

    public String getMessage() {
        return message;
    }

    public boolean isShowStatus() {
        return showStatus;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Enum<TypeComplain> getTypeComplainEnum() {
        return typeComplainEnum;
    }
}
