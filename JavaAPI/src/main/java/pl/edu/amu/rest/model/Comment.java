package pl.edu.amu.rest.model;


import java.sql.Date;
import java.sql.Timestamp;

public class Comment {

    private String id;
    private Long giverId;
    private Long recieverId;
    private Long offerId;
    private String commentText;
    private boolean positive;
    private Timestamp createdAt;

    public String getId() {
        return id;
    }

    public Long getGiverId() {
        return giverId;
    }

    public void setGiverId(Long giverId) {
        this.giverId = giverId;
    }

    public Long getRecieverId() {
        return recieverId;
    }

    public void setRecieverId(Long recieverId) {
        this.recieverId = recieverId;
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

    public boolean isPositive() {
        return positive;
    }

    public void setPositive(boolean positive) {
        this.positive = positive;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt() {
        java.util.Date utilDate = new java.util.Date();             //data w formacie util
        this.createdAt = new java.sql.Timestamp(utilDate.getTime());     //pobranie aktualnej daty i konwersja na format sql}
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }


    public Comment() {

    }


    public Comment(String id, Long giverId, Long recieverId, Long offerId, String commentText, boolean positive, Timestamp createdAt) {
        this.id = id;
        this.giverId = giverId;
        this.recieverId = recieverId;
        this.offerId = offerId;
        this.commentText = commentText;
        this.positive = positive;
        this.createdAt = createdAt;
    }

    public Comment(Long giverId, Long recieverId, Long offerId, String commentText, boolean positive) {
        this.giverId = giverId;
        this.recieverId = recieverId;
        this.offerId = offerId;
        this.commentText = commentText;
        this.positive = positive;
        this.setCreatedAt();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;
        if (id != comment.id) return false;

        return true;
    }


    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", giverId=" + giverId +
                ", recieverId=" + recieverId +
                ", offerId=" + offerId +
                ", commentText='" + commentText + '\'' +
                ", positive=" + positive +
                ", createdAt=" + createdAt +
                '}';
    }
}