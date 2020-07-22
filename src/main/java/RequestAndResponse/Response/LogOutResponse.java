package RequestAndResponse.Response;

public class LogOutResponse extends Response {


    private boolean success;

    public LogOutResponse(boolean success){
        this.success=success;
    }

    @Override
    public void execute() {

    }
}
