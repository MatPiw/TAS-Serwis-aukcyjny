package pl.edu.amu.rest.model;


import java.sql.Date;

public class Bid {
    private int id;
    private int offerId;
    private int bidderId;
    private float price;
    private Date createdAt;

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public int getOfferId() {return offerId;}

    public void setOfferId(int offerId) {this.offerId = offerId;}

    public int getBidderId() {return bidderId;}

    public void setBidderId(int bidderId) { this.bidderId = bidderId; }

    public float getPrice() {return price;}

    public void setPrice(float price) {this.price = price;}

    public Date getCreatedAt() {return createdAt;}

    public void setCreatedAt(Date createdAt) {this.createdAt = createdAt;}

    public Bid() {
        //
    }

    public Bid (int offerId, int bidderId, float price) {
        this.offerId = offerId;
        this.bidderId = bidderId;
        this.price = price;
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
