package se.iths.ateam.bring2you.Utils;

import android.media.Image;

import java.io.Serializable;

public class ListItemInfo implements Serializable {
    private String adress;
    private String name;
    private String postalCode;
    private String senderId;
    private String signedBy;
    private String id;
    private String signImageUrl;
    private String date;
    private String time;
    private boolean delivered;

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSignImageUrl() {
        return signImageUrl;
    }

    public void setSignImageUrl(String signImageUrl) {
        this.signImageUrl = signImageUrl;
    }

    public String getSignedBy() {
        return signedBy;
    }

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

    public ListItemInfo(String name, String adress, String postalCode, String senderId) {
        this.name = name;
        this.adress = adress;
        this.postalCode = postalCode;
        this.senderId = senderId;
    }


}
