package RequestAndResponse.Requests;

public class Request {


    private String RequestType;
    private String applicator;
    public void execute(){}


    public String getRequestType() {
        return RequestType;
    }

    public void setRequestType(String requestType) {
        RequestType = requestType;
    }

    public String getApplicator() {
        return applicator;
    }

    public void setApplicator(String applicator) {
        this.applicator = applicator;
    }
}
