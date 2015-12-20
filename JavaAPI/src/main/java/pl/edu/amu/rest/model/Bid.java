package pl.edu.amu.rest.model;


import java.sql.Date;
import java.sql.Timestamp;

public class Bid {
    private int id;
    private int offerId;
    private int bidderId;
    private float price;
    private Timestamp createdAt;

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public int getOfferId() {return offerId;}

    public void setOfferId(int offerId) {this.offerId = offerId;}

    public int getBidderId() {return bidderId;}

    public void setBidderId(int bidderId) { this.bidderId = bidderId; }

    public float getPrice() {return price;}

    public void setPrice(float price) {this.price = price;}

    public Timestamp getCreatedAt() {return createdAt;}

    public void setCreatedAt() {
        java.util.Date utilDate = new java.util.Date();             //data w formacie util
        this.createdAt = new java.sql.Timestamp(utilDate.getTime());     //pobranie aktualnej daty i konwersja na format sql}
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Bid() {
        //
    }

    public Bid (int offerId, int bidderId, float price) {
        this.offerId = offerId;
        this.bidderId = bidderId;
        this.price = price;
        this.setCreatedAt();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bid bid = (Bid) o;
        if (id != bid.id) return false;

        return true;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "id='" + id + '\'' +
                ", offerId='" + offerId + '\'' +
                ", bidderId='" + bidderId + '\'' +
                ", price='" + price + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
