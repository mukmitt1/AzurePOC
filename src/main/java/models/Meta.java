package models;

public class Meta {

    private String statementDate;
    private String statementBalance;
    private String minPaymentDue;

    public String getStatementDate() {
        return statementDate;
    }

    public void setStatementDate(String statementDate) {
        this.statementDate = statementDate;
    }

    public String getStatementBalance() {
        return statementBalance;
    }

    public void setStatementBalance(String statementBalance) {
        this.statementBalance = statementBalance;
    }

    public String getMinPaymentDue() {
        return minPaymentDue;
    }

    public void setMinPaymentDue(String minPaymentDue) {
        this.minPaymentDue = minPaymentDue;
    }


    @Override
    public String toString() {
        return "Meta{" +
                "statementDate='" + statementDate + '\'' +
                ", statementBalance='" + statementBalance + '\'' +
                ", minPaymentDue='" + minPaymentDue + '\'' +
                '}';
    }
}
