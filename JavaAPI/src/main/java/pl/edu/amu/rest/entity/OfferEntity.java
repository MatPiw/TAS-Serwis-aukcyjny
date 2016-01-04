package pl.edu.amu.rest.entity;

import javax.persistence.*;
import javax.ws.rs.core.Link;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Altenfrost on 2015-12-29.
 */
@Table(name = "offers")
@Entity
@NamedQueries({
        @NamedQuery(name = "offers.findAll", query = "SELECT o FROM OfferEntity o"),
        @NamedQuery(name = "offers.findAllByOwner", query = "SELECT u FROM OfferEntity u WHERE u.owner_id=:owner"),
        @NamedQuery(name = "offers.findAllByBuyer", query = "SELECT u FROM OfferEntity u WHERE u.buyer_id=:buyer"),
        @NamedQuery(name = "offers.findAllByCategory", query = "SELECT u FROM OfferEntity u WHERE u.category=:category")
})
public class OfferEntity {

    private static final Logger LOGGER = LoggerFactory.getLogger(OfferEntity.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TITLE")
    private String title;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "PICTURE_PATH")
    private String picture_path;
    @Column(name = "OWNER_ID")
    private Long owner_id;
    @Column(name = "BUY_NOW_PRICE")
    private BigDecimal buy_now_price;
    @Column(name = "ACTIVE")
    private Boolean active;
    @Column(name = "CREATED_AT")
    private Timestamp created_at;
    @Column(name = "FINISHED_AT")
    private Timestamp finished_at;
    @Column(name = "BUYER_ID")
    private Long buyer_id;
    @Column(name = "BEST_PRICE")
    private BigDecimal best_price;
    @Column(name = "MINIMAL_PRICE")
    private BigDecimal minimal_price;
    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "WEIGHT")
    private float weight;
    @Column(name = "SIZE")
    private String size;
    @Column(name = "SHIPMENT")
    private String shipment;

    @Column(name = "CATEGORY")
    private String category;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

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

    public BigDecimal getBuy_now_price() {
        return buy_now_price;
    }

    public void setBuy_now_price(BigDecimal buy_now_price) {
        this.buy_now_price = buy_now_price;
    }

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

    public BigDecimal getBest_price() {
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

    public OfferEntity() {
    }

    public OfferEntity(String title, String description, String picture_path, Long owner_id, BigDecimal buy_now_price, Timestamp created_at, BigDecimal minimal_price, float weight, String size, String shipment, String category) {
        this.title = title;
        this.description = description;
        this.picture_path = picture_path;
        this.owner_id = owner_id;
        this.buy_now_price = buy_now_price;
        this.created_at = created_at;
        this.minimal_price = minimal_price;
        this.weight = weight;
        this.size = size;
        this.shipment = shipment;
        this.category = category;
    }
    @PostLoad
    private void postLoad() {
        LOGGER.info("postLoad: {}", toString());
    }
    public OfferEntity(String title, String description, String picture_path, Long owner_id, BigDecimal buy_now_price,String currency, Boolean active, Timestamp created_at, Timestamp finished_at, Long buyer_id, BigDecimal best_price, BigDecimal minimal_price, float weight, String size, String shipment, String category) {
        this.title = title;
        this.description = description;
        this.picture_path = picture_path;
        this.owner_id = owner_id;
        this.buy_now_price = buy_now_price;
        this.currency=currency;
        this.active = active;
        this.created_at = created_at;
        this.finished_at = finished_at;
        this.buyer_id = buyer_id;
        this.best_price = best_price;
        this.minimal_price = minimal_price;
        this.weight = weight;
        this.size = size;
        this.shipment = shipment;
        this.category = category;
    }

    public OfferEntity(String title, String description, String picture_path, Long owner_id, BigDecimal buy_now_price,String currency, Timestamp created_at, Timestamp finished_at, float weight, String size, String shipment, String category) {
        this.title = title;
        this.description = description;
        this.picture_path = picture_path;
        this.owner_id = owner_id;
        this.buy_now_price = buy_now_price;
        this.currency=currency;
        this.created_at = created_at;
        this.finished_at = finished_at;
        this.weight = weight;
        this.size = size;
        this.shipment = shipment;
        this.category = category;
    }

    @Override
    public String toString() {
        return "OfferEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", picture_path='" + picture_path + '\'' +
                ", owner_id=" + owner_id +
                ", buy_now_price=" + buy_now_price +
                ", active=" + active +
                ", created_at=" + created_at +
                ", finished_at=" + finished_at +
                ", buyer_id=" + buyer_id +
                ", best_price=" + best_price +
                ", minimal_price=" + minimal_price +
                ", currency='" + currency + '\'' +
                ", weight=" + weight +
                ", size='" + size + '\'' +
                ", shipment='" + shipment + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
