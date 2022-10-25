package com.mentor4you.model.DTO;

public class MinUserDTO {

    private int id ;

    private String name;

    private String secondName;

    private String avatar;

    public MinUserDTO() {
    }

    public MinUserDTO(int id, String name, String secondName, String avatar) {
        this.id = id;
        this.name = name;
        this.secondName = secondName;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
