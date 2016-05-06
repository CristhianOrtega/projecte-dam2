package com.proyecto.dam2.librosvidal.Clases;

/**
 * Created by Mobile on 02/05/2016.
 */
public class Message {

    private String fromName, message;
    private boolean isSelf;
    private String regIDFor;

    public Message() {
    }

    public Message(String fromName, String message, String regIDFor,boolean isSelf) {
        this.fromName = fromName;
        this.message = message;
        this.regIDFor = regIDFor;
        this.isSelf = isSelf;
    }

    public String getRegIDFor() {
        return regIDFor;
    }

    public void setRegIDFor(String regIDFor) {
        this.regIDFor = regIDFor;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSelf() {
        return isSelf;
    }

    public void setSelf(boolean isSelf) {
        this.isSelf = isSelf;
    }

}