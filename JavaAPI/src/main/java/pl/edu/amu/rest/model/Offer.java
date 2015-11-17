package pl.edu.amu.rest.model;


import java.sql.Date;

public class Offer {
    private int id;
    private String title;
    private String description;
    private String picturePath;
    private int ownerId;
    private float buyNowPrice;
    private Boolean active;
    private Date createdAt;
    private Date finishedAt;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getPicturePath() { return picturePath; }

    public void setPicturePath(String picturePath) { this.picturePath = picturePath; }

    public int getOwnerId() { return ownerId; }

    public void setOwnerId(int ownerId) { this.ownerId = ownerId; }

    public float getBuyNowPrice() { return buyNowPrice; }

    public void setBuyNowPrice(float buyNowPrice) { this.buyNowPrice = buyNowPrice; }

    public Boolean getActive() { return active; }

    public void setActive(Boolean active) { this.active = active; }

    public Date getCreatedAt() { return createdAt; }

    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getFinishedAt() { return finishedAt; }

    public void setFinishedAt(Date finishedAt) { this.finishedAt = finishedAt; }

    public Offer() {
        //
    }

    public Offer(int owner, String description, float buyNowPrice){
        this.setBuyNowPrice(buyNowPrice);
        this.setOwnerId(owner);
        this.setDescription(description);
    }

    public Offer(String description, String picturePath, int ownerId, float buyNowPrice, Boolean active){
        this.setDescription(description);
        this.setOwnerId(ownerId);
        this.setPicturePath(picturePath);
        this.setBuyNowPrice(buyNowPrice);
        this.setActive(active);
        java.util.Date utilDate = new java.util.Date();             //data w formacie util
        this.createdAt = new java.sql.Date(utilDate.getTime());     //pobranie aktualnej daty i konwersja na format sql
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Offer offer = (Offer) o;
        if (id != offer.id) return false;

        return true;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id='" +id + '\'' +
                ", title='"+ title + "'" +
                ", description='" + description + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", picturePath='" + picturePath + '\'' +
                ", buyNowPrice='" + buyNowPrice + '\'' +
                ", active='" + active + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", finishedAt='" + finishedAt + '\'' +
                '}';
    }
}
