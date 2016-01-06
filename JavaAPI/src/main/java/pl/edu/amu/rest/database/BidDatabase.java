package pl.edu.amu.rest.database;


import pl.edu.amu.rest.model.Bid;

import java.util.Collection;

/**
 * Created by Altenfrost on 2015-12-31.
 */
public interface BidDatabase {
    Bid getBid(String bidId);

    Collection<Bid> getBidWithFilters(String bidderId, String offerId);

    Bid saveBid(Bid bid);

    Bid updateBid(String bidId, Bid bid);

    Boolean deleteBid(String bidId);

    Boolean deleteBidFromAuction(String offerId);

    Boolean deleteBidFromUser(String userId);

}
