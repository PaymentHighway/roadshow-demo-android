package io.paymenthighway.phroadshowdemo;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import io.paymenthighway.phroadshowdemo.Model.TokenizedCard;

public class CardItemAdapter extends RecyclerView.Adapter<CardItemAdapter.CardDataHolder> {
    private static final String TAG = "CardItemAdapter";

    private ArrayList<TokenizedCard> tokenizedCards;
    private Resources resources;


    public static class CardDataHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView cardNumber;
        public TextView expiry;
        public RelativeLayout parentLayout;
        public CardDataHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            cardNumber = itemView.findViewById(R.id.card_number);
            expiry = itemView.findViewById(R.id.expiry);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }

    }

    public CardItemAdapter(ArrayList<TokenizedCard> tokenizedCards, Resources resources) {
        this.tokenizedCards = tokenizedCards;
        this.resources = resources;
    }


    @NonNull
    @Override
    public CardDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_card, parent, false);
        CardDataHolder vh = new CardDataHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CardDataHolder cardDataHolder, int i) {
        TokenizedCard tokenizedCard = tokenizedCards.get(i);

        cardDataHolder.image.setImageBitmap(getBrandLogo(tokenizedCard));
        cardDataHolder.cardNumber.setText(buildCardNumber(tokenizedCard));
        cardDataHolder.expiry.setText(buildExpiry(tokenizedCard));
    }

    private Bitmap getBrandLogo(TokenizedCard tokenizedCard) {
        String type = tokenizedCard.getCard().getType();

        if(type.equals("Visa")){
            return BitmapFactory.decodeResource(resources, R.drawable.visa);
        }
        return BitmapFactory.decodeResource(resources, R.drawable.mc);
    }

    private String buildCardNumber(TokenizedCard tokenizedCard) {
        String bin = tokenizedCard.getCard().getBin();
        String pan = tokenizedCard.getCard().getPartialPan();
        String stars = "******";
        return bin+stars+pan;
    }

    private String buildExpiry(TokenizedCard tokenizedCard) {
        String year = tokenizedCard.getCard().getExpireYear();
        String month = tokenizedCard.getCard().getExpireMonth();
        return "Expires: " + month + "/" + year;
    }

    @Override
    public int getItemCount() {
        return tokenizedCards == null ? 0 : tokenizedCards.size();
    }

}
