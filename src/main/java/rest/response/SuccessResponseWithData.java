package rest.response;

public class SuccessResponseWithData {
    private Object data;

    public SuccessResponseWithData(Object data) {
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
