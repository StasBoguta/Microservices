package com.mentor4you.model.DTO.coopDTO;

import com.mentor4you.model.DTO.MinUserDTO;

public class DTOstatusCoopMentee {
    private MinUserDTO mentor;

    private CoopStatus coopStatus;

    public DTOstatusCoopMentee() {
    }

    public DTOstatusCoopMentee(MinUserDTO mentor, CoopStatus coopStatus) {
        this.mentor = mentor;
        this.coopStatus = coopStatus;
    }

    public MinUserDTO getMentor() {
        return mentor;
    }

    public void setMentor(MinUserDTO mentor) {
        this.mentor = mentor;
    }

    public CoopStatus getCoopStatus() {
        return coopStatus;
    }

    public void setCoopStatus(CoopStatus coopStatus) {
        this.coopStatus = coopStatus;
    }
}
