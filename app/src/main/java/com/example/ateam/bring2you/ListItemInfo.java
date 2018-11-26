package com.example.ateam.bring2you;

import java.io.Serializable;

class ListItemInfo implements Serializable {
    private String adress;
    private String name;
    private String postalCode;
    private String senderId;
    private String signedBy;

    public String getSignedBy() {
        return signedBy;
    }

    private String id;

    public void setSignedBy(String signedBy) {
        this.signedBy = signedBy;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

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
