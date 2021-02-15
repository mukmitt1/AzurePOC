package models;

import java.util.Map;

public class ServiceRequest {

    private long uuid;
    private String accountId;
    private String name;
    private String phone;
    private String messageType;
    private String messageBody;
    private Meta meta;

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

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {
        return "ServiceRequest{" +
                "uuid=" + uuid +
                ", accountId='" + accountId + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", messageType='" + messageType + '\'' +
                ", messageBody='" + messageBody + '\'' +
                ", meta=" + meta +
                '}';
    }
}
