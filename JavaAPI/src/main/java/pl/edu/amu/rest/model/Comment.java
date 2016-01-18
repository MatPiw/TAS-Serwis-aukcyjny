package pl.edu.amu.rest.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Date;
import java.sql.Timestamp;

@ApiModel(description = "Comment model.")
public class Comment {

    private String id;
    @NotBlank(message = "{Comment.giverId.empty}")
    @Pattern(regexp = "\\d+", message = "{Comment.giverId.wrong}")
    @ApiModelProperty(required = true)
    private String giverId;
    @NotBlank(message = "{Comment.recieverId.empty}")
    @Pattern(regexp = "\\d+", message = "{Comment.recieverId.wrong}")
    @ApiModelProperty(required = true)
    private String recieverId;
    @NotBlank(message = "{Comment.offerId.empty}")
    @Pattern(regexp = "\\d+", message = "{Comment.offerId.wrong}")
    @ApiModelProperty(required = true)
    private String offerId;
    @NotBlank(message = "{Comment.commentText.wrong}")
    @ApiModelProperty(required = true)
    private String commentText;
    @NotNull(message = "{Comment.positive.empty}")
    @ApiModelProperty(required = true)
    private Boolean positive;
    private Timestamp createdAt;

    public String getId() {
        return id;
    }

    public String getGiverId() {
        return giverId;
    }

    public void setGiverId(String giverId) {
        this.giverId = giverId;
    }

    public String getRecieverId() {
        return recieverId;
    }

    public void setRecieverId(String recieverId) {
        this.recieverId = recieverId;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public Boolean getPositive() {
        return positive;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Boolean isPositive() {
        return positive;
    }

    public void setPositive(Boolean positive) {
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


    public Comment(String id, String giverId, String recieverId, String offerId, String commentText, Boolean positive, Timestamp createdAt) {
        this.id = id;
        this.giverId = giverId;
        this.recieverId = recieverId;
        this.offerId = offerId;
        this.commentText = commentText;
        this.positive = positive;
        this.createdAt = createdAt;
    }

    public Comment(String giverId, String recieverId, String offerId, String commentText, Boolean positive) {
        this.giverId = giverId;
        this.recieverId = recieverId;
        this.offerId = offerId;
        this.commentText = commentText;
        this.positive = positive;
        this.setCreatedAt();;
    }

    public Comment(String commentText, boolean positive) {
        this.commentText = commentText;
        this.positive = positive;
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