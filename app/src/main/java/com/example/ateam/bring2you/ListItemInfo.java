package com.example.ateam.bring2you;

public class ListItemInfo {
    private String adress;
    private String name;
    private String postalCode;
    private String senderId;

    public String id;

    public String getAdress() {
        return adress;
    }

    public String getName() {
        return name;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getSenderId() {
        return senderId;
    }

    public ListItemInfo(){
    }

    public ListItemInfo(String adress, String name, String postalCode, String senderId) {
        this.adress = adress;
        this.name = name;
        this.postalCode = postalCode;
        this.senderId = senderId;
    }


}
