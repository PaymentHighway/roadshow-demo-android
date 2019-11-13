package io.paymenthighway.phroadshowdemo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.UUID;

import io.paymenthighway.phroadshowdemo.Model.TokenizedCard;

public class HorizontalCardItemAdapter extends RecyclerView.Adapter<HorizontalCardItemAdapter.CardDataHolder> {

    private ArrayList<TokenizedCard> tokenizedCards;
    private Context context;
    private Resources resources;
    private CardDataHolder selected;


    public static class CardDataHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView cardNumber;
        public TextView expiry;
        public UUID token;
        public RelativeLayout cardLayout;
        public CardView parentLayout;

        public CardDataHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            cardNumber = itemView.findViewById(R.id.card_number);
            expiry = itemView.findViewById(R.id.expiry);
            cardLayout = itemView.findViewById(R.id.card_layout);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }

    }

    public HorizontalCardItemAdapter(ArrayList<TokenizedCard> tokenizedCards, Resources resources, Context context) {
        this.tokenizedCards = tokenizedCards;
        this.resources = resources;
        this.context = context;
    }


    @NonNull
    @Override
    public CardDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_card_horizontal, parent, false);
        CardDataHolder vh = new CardDataHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CardDataHolder cardDataHolder, int i) {
        TokenizedCard tokenizedCard = tokenizedCards.get(i);
        cardDataHolder.image.setImageBitmap(getBrandLogo(tokenizedCard));
        cardDataHolder.cardNumber.setText(buildCardNumber(tokenizedCard));
        cardDataHolder.expiry.setText(buildExpiry(tokenizedCard));
        cardDataHolder.token = tokenizedCard.getCardToken();

        cardDataHolder.cardLayout.setOnClickListener(v -> {
            updateSelected(cardDataHolder);
        });
    }

    private Bitmap getBrandLogo(TokenizedCard tokenizedCard) {
        String type = tokenizedCard.getCard().getType();

        if(type.equals("Visa")){
            return BitmapFactory.decodeResource(resources, R.drawable.visa);
        }
        return BitmapFactory.decodeResource(resources, R.drawable.mc);
    }

    private String buildCardNumber(TokenizedCard tokenizedCard) {
        return "*** " + tokenizedCard.getCard().getPartialPan();

    }

    private String buildExpiry(TokenizedCard tokenizedCard) {
        String year = tokenizedCard.getCard().getExpireYear();
        String month = tokenizedCard.getCard().getExpireMonth();
        return "Expires: " + month + "/" + year;
    }

    private void updateSelected(CardDataHolder holder) {
        if(selected != null) {
            selected.parentLayout.setCardBackgroundColor(ContextCompat.getColor(context, R.color.credit_card));
        }
        selected = holder;
        holder.parentLayout.setCardBackgroundColor(ContextCompat.getColor(context, R.color.credit_card_selected));
    }

    @Override
    public int getItemCount() {
        return tokenizedCards == null ? 0 : tokenizedCards.size();
    }

    public UUID getSelectedToken() {
        return selected != null ? selected.token : null;
    }
}
