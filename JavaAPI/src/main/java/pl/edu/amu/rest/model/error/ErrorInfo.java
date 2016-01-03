package pl.edu.amu.rest.model.error;

import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorInfo {
    private String message;
    private String usermessage;
    private int errorcode;
    public String getUsermessage() {
        return usermessage;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public String getMessage() {
        return message;
    }
    //WYWOŁUJE WSZYSTKIE GETTERY
    @Override
    public String toString() {
        return "ErrorInfo{" +
                "message='" + message + '\'' +
                ", usermessage='" + usermessage + '\'' +
                ", errorcode=" + errorcode +
                '}';
    }

    public ErrorInfo(String message, String usermessage, int errorcode) {
        this.message = message;
        this.usermessage = usermessage;
        this.errorcode = errorcode;
    }



}