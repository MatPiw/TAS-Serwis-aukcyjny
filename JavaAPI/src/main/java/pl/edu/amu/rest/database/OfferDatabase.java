package pl.edu.amu.rest.database;

import pl.edu.amu.rest.model.Offer;

import java.util.Collection;

/**
 * Created by Altenfrost on 2015-12-31.
 */
public interface OfferDatabase {
    Offer getOffer(String offerId);

    Offer updateOffer(String offerId, Offer offer);

    Offer saveOffer(Offer offer);

    Boolean deleteOffer(final String offerId);

    Boolean deleteOffersByOwnerId(String owner_id);

    Collection<Offer> getOffersByOwner(String uid);

    /*Collection<Offer> getOffersByBuyer(String uid);

    Collection<Offer> getOffersByCategory(String category);
*/
    Collection<Offer> getOffersWithFilters(String owner_id, String buyer_id, String category);

}
