package io.paymenthighway.phroadshowdemo.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/* Just dummy response to identify successful charge or soft decline */

public class ChargeResponse {
    @Expose
    @SerializedName("response_code")
    private String responseCode;

    @SerializedName("three_d_secure_url")
    private String threeDSecureUrl;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getThreeDSecureUrl() {
        return threeDSecureUrl;
    }

    public void setThreeDSecureUrl(String threeDSecureUrl) {
        this.threeDSecureUrl = threeDSecureUrl;
    }

}
