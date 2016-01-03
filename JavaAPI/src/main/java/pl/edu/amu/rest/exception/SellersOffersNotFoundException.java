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
public class SellersOffersNotFoundException  extends ClientErrorException {
    private static String messageKey="SellersOffersNotFound";
    ResourceBundle labels=ResourceBundle.getBundle("offermessage");

    @Override
    public String getLocalizedMessage() {
        return labels.getString(messageKey);
    }

    public SellersOffersNotFoundException() {
        super(Response.status(Response.Status.NOT_FOUND)
                .type("application/json")
                .encoding("UTF-8")
                .build());


    }


    public SellersOffersNotFoundException(String message) {
        super(message, Response.Status.NOT_FOUND);

    }
}