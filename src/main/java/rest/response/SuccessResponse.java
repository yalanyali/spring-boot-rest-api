package rest.response;

import java.util.Date;

public class SuccessResponse {
    private Object data;

    public SuccessResponse(Object data) {
        super();
        this.data = data;
    }

    public String getMessage() {
        String message = "success";
        return message;
    }

    public Object getData() {
        return data;
    }
}
