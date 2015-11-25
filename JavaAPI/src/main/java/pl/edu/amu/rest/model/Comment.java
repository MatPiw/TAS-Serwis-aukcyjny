package pl.edu.amu.rest.model;



import java.sql.Date;
import java.sql.Timestamp;

public class Comment {

    private int id;
    private int giverId;
    private int recieverId;
    private int offerId;
    private String comment;
    private boolean positive;
    private Timestamp createdAt;


    public int getId(){return id;}
    public void setId(int id){this.id = id;}
    public int getgiverId(){return giverId;}
    public void setGiverId(int id){this.giverId = id;}
    public int getRecieverId(){return recieverId;}
    public void setRecieverId(int id){this.recieverId = id;}
    public int getOfferId(){return offerId;}
    public void setOfferId(int id){this.offerId = id;}
    public String getComment(){return comment;}
    public void setComment(String comment){this.comment = comment;}
    public boolean getPositive(){return positive;}
    public void setPositive(boolean positive){ this.positive = positive;}
    public Timestamp getCreatedAt(){return createdAt;}

    public void setCreatedAt() {
        java.util.Date utilDate = new java.util.Date();             //data w formacie util
        this.createdAt = new java.sql.Timestamp(utilDate.getTime());     //pobranie aktualnej daty i konwersja na format sql}
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }


    public Comment(){

    }
    public Comment(int id, int giverId, int offerId, int recieverId, String comment, boolean positive){

        this.setId(id);
        this.setGiverId(giverId);
        this.setOfferId(offerId);
        this.setRecieverId(recieverId);
        this.setComment(comment);
        this.setPositive(positive);
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
                "id='" +id + '\'' +
                ", giverId='"+ giverId + '\'' +
                ", offedId='" + offerId + '\'' +
                ", recieverId'" + recieverId + '\'' +
                ", comment='" + comment + '\'' +
                ", positive='" + positive + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }







}