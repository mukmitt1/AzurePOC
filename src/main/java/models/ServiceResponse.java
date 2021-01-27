package models;

public class ServiceResponse {

    private long uuid;
    private String additionalStatusCode;
    private String additionalStatusMsg;


    public String getAdditionalStatusCode() {
        return additionalStatusCode;
    }

    public void setAdditionalStatusCode(String additionalStatusCode) {
        this.additionalStatusCode = additionalStatusCode;
    }

    public String getAdditionalStatusMsg() {
        return additionalStatusMsg;
    }

    public void setAdditionalStatusMsg(String additionalStatusMsg) {
        this.additionalStatusMsg = additionalStatusMsg;
    }

    public long getUuid() {
        return uuid;
    }

    public void setUuid(long uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "ServiceResponse{" +
                "uuid=" + uuid +
                ", additionalStatusCode='" + additionalStatusCode + '\'' +
                ", additionalStatusMsg='" + additionalStatusMsg + '\'' +
                '}';
    }
}
