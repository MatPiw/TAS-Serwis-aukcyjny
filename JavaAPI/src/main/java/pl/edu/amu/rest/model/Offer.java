package pl.edu.amu.rest.model;


import java.sql.Timestamp;

public class Offer {

    private Long id;

    private String title;

    private String description;

    private String picture_path;

    private Long owner_id;

    private Prices prices;

    private Boolean active;

    private Timestamp created_at;

    private Timestamp finished_at;

    private Long buyer_id;

    private float weight;

    private String size;

    private String shipment;


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

    public Long getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(Long owner_id) {
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

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getFinished_at() {
        return finished_at;
    }

    public void setFinished_at(Timestamp finished_at) {
        this.finished_at = finished_at;
    }

    public Long getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(Long buyer_id) {
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
        java.util.Date utilDate = new java.util.Date();             //data w formacie util
        this.created_at = new Timestamp(utilDate.getTime());     //pobranie aktualnej daty i konwersja na format sql}
    }

    public Timestamp getFinishedAt() { return finished_at; }

    public void setFinishedAt() {
        java.util.Date utilDate = new java.util.Date();             //data w formacie util
        this.finished_at = new Timestamp(utilDate.getTime()+(14*24*60*60*1000));     //pobranie aktualnej daty i konwersja na format sql plus 14 dni}
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.created_at = createdAt;
    }

    public void setFinishedAt(Timestamp finishedAt) {
        this.finished_at = finishedAt;
    }
    public Offer() {
        //
    }

    public Offer(Long id, String title, String description, String picture_path, Long owner_id, Prices prices, Boolean active, Timestamp created_at, Timestamp finished_at, Long buyer_id, float weight, String size, String shipment, String category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.picture_path = picture_path;
        this.owner_id = owner_id;
        this.prices = prices;
        this.active = active;
        this.created_at = created_at;
        this.finished_at = finished_at;
        this.buyer_id = buyer_id;
        this.weight = weight;
        this.size = size;
        this.shipment = shipment;
        this.category = category;
    }

    public Offer(String title, String picture_path, String description, Long owner_id, Prices prices, float weight, String size, String shipment, String category) {
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

    public Offer(String title, String description, String picture_path, Long owner_id, Prices prices, Boolean active, Timestamp finished_at, Long buyer_id, float weight, String size, String shipment, String category) {
        this.title = title;
        this.description = description;
        this.picture_path = picture_path;
        this.owner_id = owner_id;
        this.prices = prices;
        this.active = active;
        this.finished_at = finished_at;
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
                ", created_at=" + created_at +
                ", finished_at=" + finished_at +
                ", buyer_id=" + buyer_id +
                ", weight=" + weight +
                ", size='" + size + '\'' +
                ", shipment='" + shipment + '\'' +
                ", category='" + category + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }
}
