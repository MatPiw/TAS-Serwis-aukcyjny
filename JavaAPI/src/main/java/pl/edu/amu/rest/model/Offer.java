package pl.edu.amu.rest.model;


import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.util.Date;

public class Offer {


    private String id;
    @NotBlank(message = "{Offer.title.empty}")
    @Pattern(message = "{Offer.title.wrong}",regexp = "[a-zA-ZęóąśłżźćńĘÓĄŚŁŻŹĆŃ]{1,20}[^0-9]{3,15}")
    private String title;
    @NotBlank(message = "{Offer.description.empty}")
    private String description;
    @NotBlank(message = "{Offer.picture_path.empty}")
    private String picture_path;
    @NotNull(message = "{Offer.owner_id.empty}")
    private String owner_id;
    @NotNull(message = "{Offer.Prices.empty}")
    @Valid
    private Prices prices;

    private Boolean active;

    private Timestamp createdAt;

    private Timestamp finishedAt;

    private String buyer_id;
    @Min(value = 1L,message = "{Offer.weight.wrong}")
    private float weight;
    @NotBlank(message = "{Offer.size.empty}")
    @Pattern(regexp = "[0-9]+x[0-9]+x[0-9]+", message = "{Offer.size.wrong}")
    private String size;
    @NotBlank(message = "{Offer.shipment.empty}")
    private String shipment;

    @NotBlank(message = "{Offer.category.empty}")
    @Pattern(regexp = "[^0-9]+", message = "{Offer.category.wrong}")
    private String category;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture_path() {
        return picture_path;
    }

    public void setPicture_path(String picture_path) {
        this.picture_path = picture_path;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    /*public BigDecimal getBuy_now_price() {
        return buy_now_price;
    }

    public void setBuy_now_price(BigDecimal buy_now_price) {
        this.buy_now_price = buy_now_price;
    }*/

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public String getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(String buyer_id) {
        this.buyer_id = buyer_id;
    }

   /* public BigDecimal getBest_price() {
        return best_price;
    }

    public void setBest_price(BigDecimal best_price) {
        this.best_price = best_price;
    }

    public BigDecimal getMinimal_price() {
        return minimal_price;
    }

    public void setMinimal_price(BigDecimal minimal_price) {
        this.minimal_price = minimal_price;
    }*/

    public Prices getPrices() {
        return prices;
    }

    public void setPrices(Prices prices) {
        this.prices = prices;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getShipment() {
        return shipment;
    }

    public void setShipment(String shipment) {
        this.shipment = shipment;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCreatedAt() {
        Date utilDate = new Date();             //data w formacie util
        this.createdAt = new Timestamp(utilDate.getTime());     //pobranie aktualnej daty i konwersja na format sql}
    }

    public Timestamp getFinishedAt() { return finishedAt; }

    public void setFinishedAt() {
        Date utilDate = new Date();             //data w formacie util
        this.finishedAt = new Timestamp(utilDate.getTime()+(14*24*60*60*1000));     //pobranie aktualnej daty i konwersja na format sql plus 14 dni}
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setFinishedAt(Timestamp finishedAt) {
        this.finishedAt = finishedAt;
    }
    public Offer() {
        //
    }

    public Offer(String id, String title, String description, String picture_path, String owner_id, Prices prices, Boolean active, Timestamp created_at, Timestamp finished_at, String buyer_id, float weight, String size, String shipment, String category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.picture_path = picture_path;
        this.owner_id = owner_id;
        this.prices = prices;
        this.active = active;
        this.createdAt = created_at;
        this.finishedAt = finished_at;
        this.buyer_id = buyer_id;
        this.weight = weight;
        this.size = size;
        this.shipment = shipment;
        this.category = category;
    }

    public Offer(String title, String picture_path, String description, String owner_id, Prices prices, float weight, String size, String shipment, String category) {
        this.title = title;
        this.picture_path = picture_path;
        this.description = description;
        this.owner_id = owner_id;
        this.active= true;
        this.prices = prices;
        this.buyer_id = null;
        this.weight = weight;
        this.size = size;
        this.shipment = shipment;
        this.category = category;
        this.setCreatedAt();
        this.setFinishedAt();
    }

    public Offer(String title, String description, String picture_path, String owner_id, Prices prices, Boolean active, Timestamp finished_at, String buyer_id, float weight, String size, String shipment, String category) {
        this.title = title;
        this.description = description;
        this.picture_path = picture_path;
        this.owner_id = owner_id;
        this.prices = prices;
        this.active = active;
        this.finishedAt = finished_at;
        this.buyer_id = buyer_id;
        this.weight = weight;
        this.size = size;
        this.shipment = shipment;
        this.category = category;
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
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", picture_path='" + picture_path + '\'' +
                ", owner_id=" + owner_id +
                ", prices=" + prices +
                ", active=" + active +
                ", createdAt=" + createdAt +
                ", finishedAt=" + finishedAt +
                ", buyer_id=" + buyer_id +
                ", weight=" + weight +
                ", size='" + size + '\'' +
                ", shipment='" + shipment + '\'' +
                ", category='" + category + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }
}
