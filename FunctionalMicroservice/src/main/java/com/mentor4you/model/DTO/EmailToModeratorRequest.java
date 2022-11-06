package com.mentor4you.model.DTO;


public class EmailToModeratorRequest {
    private int emailAdrId;
    private String emailAdres;
    private String name;
    private  String subject;
    private String message;

    public EmailToModeratorRequest() {
    }

    public EmailToModeratorRequest(int emailAdrId, String emailAdres, String name, String subject, String message) {
        this.emailAdrId = emailAdrId;
        this.emailAdres = emailAdres;
        this.name = name;
        this.subject = subject;
        this.message = message;
    }

    public int getEmailAdrId() {
        return emailAdrId;
    }

    public String getEmailAdres() {
        return emailAdres;
    }

    public String getName() {
        return name;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }
}
