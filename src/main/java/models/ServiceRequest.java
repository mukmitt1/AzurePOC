package models;

import java.util.Map;

public class ServiceRequest {

    private long uuid;
    private String messageType;
    private String messageBody;
    Map<String, String> additionalFields;


    public long getUuid() {
        return uuid;
    }

    public void setUuid(long uuid) {
        this.uuid = uuid;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public Map<String, String> getAdditionalFields() {
        return additionalFields;
    }

    public void setAdditionalFields(Map<String, String> additionalFields) {
        this.additionalFields = additionalFields;
    }

    @Override
    public String toString() {
        return "ServiceRequest{" +
                "uuid=" + uuid +
                ", messageType='" + messageType + '\'' +
                ", messageBody='" + messageBody + '\'' +
                ", additionalFields=" + additionalFields +
                '}';
    }
}
