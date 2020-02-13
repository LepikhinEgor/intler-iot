package intler_iot.controllers.entities;

import java.util.Objects;

public class RegistrationMessage {
    public static final int SUCCESS = 0;
    public static final int FAIL = 1;
    public static final int LOGIN_INVALID = 2;
    public static final int EMAIL_INVALID = 3;
    public static final int PASSWORD_INVALID = 4;
    public static final int LOGIN_BUSY = 5;
    public static final int EMAIL_BUSY = 6;

    private int status;
    private String message;

    public RegistrationMessage(int status) {
        this.status = status;

        switch(status) {
            case SUCCESS: message = "Success registration";break;
            case FAIL: message = "Registration fail";break;
            case LOGIN_INVALID: message = "Invalid login";break;
            case EMAIL_INVALID: message = "Invalid email";break;
            case PASSWORD_INVALID: message = "Invalid password";break;
            case LOGIN_BUSY: message = "Login already busy";break;
            case EMAIL_BUSY: message = "Email already busy";break;
        }
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RegistrationMessage{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistrationMessage that = (RegistrationMessage) o;
        return status == that.status &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, message);
    }
}
