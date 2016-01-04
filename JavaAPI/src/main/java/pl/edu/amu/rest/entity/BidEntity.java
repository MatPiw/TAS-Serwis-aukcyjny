package pl.edu.amu.rest.entity;

import javax.persistence.*;
import javax.ws.rs.core.Link;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Altenfrost on 2015-12-29.
 */
@Table(name = "bids")

public class BidEntity {

    private Long id;

    private Long offer_id;

    private Long bidder_id;

    private BigDecimal price_offered;

    private Timestamp created_at;

}
