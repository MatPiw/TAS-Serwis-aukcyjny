package pl.edu.amu.rest.exception;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by Altenfrost on 2016-01-03.
 */
public class NotFoundOfferUpdateException extends ClientErrorException {
    /*private static String messageKey="notFoundOfferUpdateData";*/
    private static long serialVersionUID = 1L;
    private ArrayList<String> messageKeys=new ArrayList<>();
    private String bundle;
    ResourceBundle labels;

    @Override
    public String getLocalizedMessage() {
        String result=new String("Niestety, ale nie znaleziono: ");
        for (String messageKey: messageKeys){
            result+=labels.getString(messageKey);
        }
        return result;
    }


    public NotFoundOfferUpdateException(String message, HashMap<String, Boolean> errors) {
        super(message, Response.Status.NOT_FOUND);

        for (Map.Entry<String,Boolean> entry:errors.entrySet()){
            if (entry.getValue()){
                messageKeys.add(entry.getKey());
            }
        }



    }
}
