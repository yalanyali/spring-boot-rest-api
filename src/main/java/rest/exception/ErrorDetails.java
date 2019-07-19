package rest.exception;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class ErrorDetails {
    private Date timestamp;
    private String message;
    private List<String> details;

    public ErrorDetails(Date timestamp, String message, List<String> details) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public ErrorDetails(String message, List<String> details) {
        super();
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.message = message;
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getDetails() {
        return details;
    }

}