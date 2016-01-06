package pl.edu.amu.rest.entity;

import javax.persistence.*;
import javax.ws.rs.core.Link;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Altenfrost on 2015-12-29.
 */
@Table(name = "bids")
@Entity
@NamedQueries({
        @NamedQuery(name = "bids.findAll", query = "SELECT b FROM BidEntity b"),
        @NamedQuery(name = "bids.findAllByAuction", query = "SELECT b FROM BidEntity b WHERE b.offerId=:offerId"),
        @NamedQuery(name = "bids.findAllByUser", query = "SELECT b FROM BidEntity b WHERE b.bidderId=:bidderId")
        /*@NamedQuery(name = "offers.findAllByBuyer", query = "SELECT u FROM OfferEntity u WHERE u.buyer_id=:buyer"),
        @NamedQuery(name = "comments.findAllByUser", query = "SELECT c FROM OfferEntity u WHERE u.category=:category"),*/

})
public class BidEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "OFFER_ID")
    private Long offerId;
    @Column(name = "BIDDER_ID")
    private Long bidderId;
    @Column(name = "PRICE")
    private BigDecimal priceOffered;
    @Column(name = "CREATED_AT")
    private Timestamp createdAt;

    public Long getId() {
        return id;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public Long getBidderId() {
        return bidderId;
    }

    public void setBidderId(Long bidderId) {
        this.bidderId = bidderId;
    }

    public BigDecimal getPriceOffered() {
        return priceOffered;
    }

    public void setPriceOffered(BigDecimal priceOffered) {
        this.priceOffered = priceOffered;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public BidEntity() {
    }

    public BidEntity(Long offerId, Long bidderId, BigDecimal priceOffered, Timestamp createdAt) {
        this.offerId = offerId;
        this.bidderId = bidderId;
        this.priceOffered = priceOffered;
        this.createdAt = createdAt;
    }
}
