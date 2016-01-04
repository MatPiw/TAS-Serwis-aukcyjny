/*
package pl.edu.amu.repository;


import pl.edu.amu.rest.model.Comment;
import pl.edu.amu.tools.DBConnection;
import pl.edu.amu.tools.DBOperator;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class CommentRepository {

    private DBConnection database;
    private DBOperator operator;
    private Connection connection;

    public List<Comment> getComments(){
        return operator.getAllComments(connection);
    }

    public CommentRepository() {
        try {
            database= new DBConnection();
            connection = database.getConnection();
            operator= new DBOperator();
            //operator.getAllComments(connection, comments);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Blad polaczenia z Baza Danych.");
        }
        //users.add(new User("huecov", "h123"));
    }

    public Comment save(Comment comment)  {

        //tu jest problem, bo id przydziela baza danych a nie bezposrednio api. przez to jest problem z zapisywaniem ofert
        //Comment dbComment = findById(comment.getId());
            try {
                operator.saveComment(connection, comment);
            } catch (Exception e) {
                e.printStackTrace();
            }
        //}
        return null;        //potem wywalic
        //return dbComment;
    }

    */
/*public Comment findById(int id) {
        List<Comment> comments = getComments();
        for(Comment comment : comments){
            if (id == comment.getId()){
                return comment;
            }
        }
        return null;
    }*//*

}
*/
