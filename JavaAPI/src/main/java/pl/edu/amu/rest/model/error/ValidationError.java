package pl.edu.amu.rest.model.error;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Altenfrost on 2015-12-30.
 */
@XmlRootElement
public class ValidationError {
    private String message;

    private String messageTemplate;


    private String invalidValue;
    public ValidationError(String message, String messageTemplate, String invalidValue) {
        this.message = message;
        this.messageTemplate = messageTemplate;
        this.invalidValue = invalidValue;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageTemplate() {
        return messageTemplate;
    }

    public String getInvalidValue() {
        return invalidValue;
    }
}
