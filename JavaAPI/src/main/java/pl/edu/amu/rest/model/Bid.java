package pl.edu.amu.rest.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@ApiModel(description = "Bid model.")
public class Bid {
    private String id;
    @NotBlank(message = "{Bid.offerId.empty}")
    @Pattern(regexp = "\\d+", message = "{Bid.offerId.wrong}")
    @ApiModelProperty(required = true)
    private String offerId;
    @NotBlank(message = "{Bid.bidderId.empty}")
    @Pattern(regexp = "\\d+", message = "{Bid.bidderId.wrong}")
    @ApiModelProperty(required = true)
    private String bidderId;
    @NotNull(message = "{Bid.price.empty}")
    @DecimalMin(value = "0.0",message = "{Bid.price.toolow}")
    @ApiModelProperty(required = true)
    private BigDecimal price;
    private Timestamp createdAt;

    public String getId() {
        return id;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getBidderId() {
        return bidderId;
    }

    public void setBidderId(String bidderId) {
        this.bidderId = bidderId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

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

    public Bid(String id, String offerId, String bidderId, BigDecimal price, Timestamp createdAt) {
        this.id = id;
        this.offerId = offerId;
        this.bidderId = bidderId;
        this.price = price;
        this.createdAt = createdAt;
    }

    public Bid(String offerId, String bidderId, BigDecimal price) {
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
                ", price=" + price +
                ", createdAt=" + createdAt +
                '}';
    }
}
