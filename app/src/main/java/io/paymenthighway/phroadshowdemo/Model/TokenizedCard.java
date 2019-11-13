package io.paymenthighway.phroadshowdemo.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class TokenizedCard {
    @Expose
    @SerializedName("card_token")
    private UUID cardToken;
    @SerializedName("card")
    private Card card;

    public UUID getCardToken() {
        return cardToken;
    }

    public void setCardToken(UUID cardToken) {
        this.cardToken = cardToken;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}

