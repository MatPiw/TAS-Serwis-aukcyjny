package pl.edu.amu.rest.database;

import pl.edu.amu.rest.model.Comment;

import java.util.Collection;

/**
 * Created by Altenfrost on 2015-12-31.
 */
public interface CommentDatabase {
    Comment getComment(String commentId);

    Collection<Comment> getCommentsWithFilters(String giverId, String receiverId, String offerId);

    Comment updateComment(String commentId, Comment comment);

    Comment saveComment(Comment comment);

    Boolean deleteComment(String commentId);

    Boolean deleteCommentsFromAuction(String offerId);

    Boolean deleteCommentsFromUser(String userId);//nie wiem, czy potrzebne. On jest od usuwania komentarzy tego usera, zarówno tych przez niego otrzymanych jak i danych

}
