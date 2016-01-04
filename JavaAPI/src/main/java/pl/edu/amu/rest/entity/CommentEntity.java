package pl.edu.amu.rest.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Altenfrost on 2015-12-29.
 */
@Table(name = "comments")
@Entity
@NamedQueries({
        @NamedQuery(name = "comments.findAll", query = "SELECT c FROM CommentEntity c"),
        @NamedQuery(name = "comments.findAllByAuction", query = "SELECT c FROM CommentEntity c WHERE c.offerId=:offerId"),
        @NamedQuery(name = "comments.findAllByUser", query = "SELECT c FROM CommentEntity c WHERE c.giverId=:userId OR c.receiverId=:userId"),
        /*@NamedQuery(name = "offers.findAllByBuyer", query = "SELECT u FROM OfferEntity u WHERE u.buyer_id=:buyer"),
        @NamedQuery(name = "comments.findAllByUser", query = "SELECT c FROM OfferEntity u WHERE u.category=:category"),*/
        @NamedQuery(name = "comments.deleteAllByOwnerId", query = "DELETE FROM OfferEntity u WHERE u.owner_id=:owner_id")
})
public class CommentEntity {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentEntity.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "GIVER_ID")
    private Long giverId;

    @Column(name = "RECEIVER_ID")
    private Long receiverId;

    @Column(name = "OFFER_ID")
    private Long offerId;

    @Column(name = "COMMENT")
    private String commentText;

    @Column(name = "POSITIVE")
    private Boolean positive;

    @Column(name = "CREATED_AT")
    private Timestamp createdAt;

    public Long getGiverId() {
        return giverId;
    }

    public void setGiverId(Long giverId) {
        this.giverId = giverId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Boolean getPositive() {
        return positive;
    }

    public void setPositive(Boolean positive) {
        this.positive = positive;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }
    @PostLoad
    private void postLoad() {
        LOGGER.info("postLoad: {}", toString());
    }

    public CommentEntity() {
    }

    public CommentEntity(Long receiverId, Long offerId, String commentText, Boolean positive, Timestamp createdAt) {
        this.receiverId = receiverId;
        this.offerId = offerId;
        this.commentText = commentText;
        this.positive = positive;
        this.createdAt = createdAt;
    }
}
