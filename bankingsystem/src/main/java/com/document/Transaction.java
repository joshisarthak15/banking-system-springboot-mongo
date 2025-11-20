package com.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "transactions")
public class Transaction {

    @Id
    private String id;

    private String transactionId;
    private String type;
    private Double amount;
    private String status;
    private Date timestamp;

    private String sourceAccount;
    private String destinationAccount;

    public Transaction() { }

    public Transaction(String id, String transactionId, String type, Double amount, String status,
                       Date timestamp, String sourceAccount, String destinationAccount) {
        this.id = id;
        this.transactionId = transactionId;
        this.type = type;
        this.amount = amount;
        this.status = status;
        this.timestamp = timestamp;
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
    }

    // ----- Getters and Setters -----

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(String sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(String destinationAccount) {
        this.destinationAccount = destinationAccount;
    }
}
