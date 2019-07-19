package rest.response;

public class SuccessResponseWithId {
    private Integer id;

    public SuccessResponseWithId(Integer id) {
        super();
        this.id = id;
    }

    public String getMessage() {
        return "success";
    }

    public Integer getId() {
        return id;
    }
}
