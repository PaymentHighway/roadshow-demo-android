package io.paymenthighway.phroadshowdemo.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Card {
    @Expose
    @SerializedName("bin")
    private String bin;
    @SerializedName("card_fingerprint")
    private String cardFingerprint;
    @SerializedName("category")
    private String category;
    @SerializedName("country_code")
    private String countryCode;
    @SerializedName("cvc_required")
    private String cvcRequired;
    @SerializedName("expire_month")
    private String expireMonth;
    @SerializedName("expire_year")
    private String expireYear;
    @SerializedName("funding")
    private String funding;
    @SerializedName("pan_fingerprint")
    private String panFingerprint;
    @SerializedName("partial_pan")
    private String partialPan;
    @SerializedName("type")
    private String type;

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public String getCardFingerprint() {
        return cardFingerprint;
    }

    public void setCardFingerprint(String cardFingerprint) {
        this.cardFingerprint = cardFingerprint;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCvcRequired() {
        return cvcRequired;
    }

    public void setCvcRequired(String cvcRequired) {
        this.cvcRequired = cvcRequired;
    }

    public String getExpireMonth() {
        return expireMonth;
    }

    public void setExpireMonth(String expireMonth) {
        this.expireMonth = expireMonth;
    }

    public String getExpireYear() {
        return expireYear;
    }

    public void setExpireYear(String expireYear) {
        this.expireYear = expireYear;
    }

    public String getFunding() {
        return funding;
    }

    public void setFunding(String funding) {
        this.funding = funding;
    }

    public String getPanFingerprint() {
        return panFingerprint;
    }

    public void setPanFingerprint(String panFingerprint) {
        this.panFingerprint = panFingerprint;
    }

    public String getPartialPan() {
        return partialPan;
    }

    public void setPartialPan(String partialPan) {
        this.partialPan = partialPan;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
