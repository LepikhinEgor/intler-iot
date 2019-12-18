package intler_iot.controllers.entities;

import java.util.Objects;

public class RestResponce {

    public static final int SUCCESS = 0;
    public static final int FAIL = 1;

    private int status;
    private String description;

    public RestResponce(int status) {
        this.status = status;

        switch (status) {
            case 0: description = "Success"; break;
            case 1: description = "Fail"; break;
        }
    }

    public RestResponce(int status, String description) {
        this.status = status;
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestResponce that = (RestResponce) o;
        return status == that.status &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, description);
    }

    @Override
    public String toString() {
        return "RestResponce{" +
                "status=" + status +
                ", description='" + description + '\'' +
                '}';
    }
}
