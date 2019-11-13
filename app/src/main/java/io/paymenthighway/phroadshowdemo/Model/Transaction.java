package io.paymenthighway.phroadshowdemo.Model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Transaction implements Comparable<Transaction>{
    @Expose
    @SerializedName("card")
    private Card card;
    @SerializedName("date")
    private String date;
    @SerializedName("committed_amount")
    private Integer committedAmount;
    @SerializedName("filing_code")
    private String filingCode;
    @SerializedName("token")
    private UUID token;
    @SerializedName("transaction_id")
    private String transactionId;

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getCommittedAmount() {
        return committedAmount;
    }

    public void setCommittedAmount(Integer committedAmount) {
        this.committedAmount = committedAmount;
    }

    public String getFilingCode() {
        return filingCode;
    }

    public void setFilingCode(String filingCode) {
        this.filingCode = filingCode;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public int compareTo(@NonNull Transaction that) {
        LocalDateTime dateTime1 = LocalDateTime.parse(this.date, DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime dateTime2 = LocalDateTime.parse(that.getDate(), DateTimeFormatter.ISO_DATE_TIME);
        return dateTime2.compareTo(dateTime1);
    }
}
