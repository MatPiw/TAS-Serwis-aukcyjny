package pl.edu.amu.rest.model;

import javax.ws.rs.core.Response;

public class ErrorResponse {

    private int httpStatus;
    private String message;
    private String userMessage;
    private String info;


    public ErrorResponse(Response.Status status, String message) {
        this.httpStatus = status.getStatusCode();
        this.message = message;
        this.userMessage = message;
        this.info = "http://localhost:8080/users";
    }

    public ErrorResponse(Response.Status status, String msg, String usrMsg) {
        this.httpStatus = status.getStatusCode();
        this.message = msg;
        this.userMessage = usrMsg;
        this.info = "http://localhost:8080/users";
    }

    public int getHttpStatus() { return httpStatus; }

    public String getMessage() {
        return message;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public String getInfo() {
        return info;
    }


}