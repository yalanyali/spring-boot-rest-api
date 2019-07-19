package rest.response;

public class UniqueCheckResponse {
    private boolean isUnique;

    public UniqueCheckResponse(boolean isUnique) {
        super();
        this.isUnique = isUnique;
    }

    public boolean isUnique() {
        return isUnique;
    }
}
