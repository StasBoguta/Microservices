package com.mentor4you.model.DTO;

public class UserBanUpdateRequest {
    private int id;
    public boolean banStatus;

    public UserBanUpdateRequest() {
    }

    public UserBanUpdateRequest(int id, boolean banStatus) {
        this.id = id;
        this.banStatus = banStatus;
    }

    public int getId() {
        return id;
    }

    public boolean isBanStatus() {
        return banStatus;
    }
}
