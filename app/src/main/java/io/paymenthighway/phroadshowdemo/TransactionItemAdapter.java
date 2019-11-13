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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import io.paymenthighway.phroadshowdemo.Model.Transaction;

public class TransactionItemAdapter extends RecyclerView.Adapter<TransactionItemAdapter.CardDataHolder> {
    private static final String TAG = "TransactionItemAdapter";

    private ArrayList<Transaction> transactions;
    private Resources resources;


    public static class CardDataHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView date;
        public TextView amount;
        public TextView lastDigits;
        public RelativeLayout parentLayout;
        public CardDataHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.transaction_brand_image);
            amount = itemView.findViewById(R.id.transaction_amount);
            date = itemView.findViewById(R.id.transaction_date);
            lastDigits = itemView.findViewById(R.id.transaction_last_digits);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }

    }

    public TransactionItemAdapter(ArrayList<Transaction> transactions, Resources resources) {
        this.transactions = transactions;
        this.resources = resources;
    }


    @NonNull
    @Override
    public CardDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_transaction, parent, false);
        return new CardDataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardDataHolder cardDataHolder, int i) {
        Transaction transaction = transactions.get(i);
        Log.d(TAG, transaction.getCommittedAmount().toString());
        cardDataHolder.image.setImageBitmap(getBrandLogo(transaction));
        cardDataHolder.date.setText(buildDateTime(transaction));
        cardDataHolder.lastDigits.setText(lastDigits(transaction));
        cardDataHolder.amount.setText(buildAmount(transaction));
    }

    private Bitmap getBrandLogo(Transaction transaction) {
        String type = transaction.getCard().getType();

        if(type.equals("Visa")){
            return BitmapFactory.decodeResource(resources, R.drawable.visa);
        }
        return BitmapFactory.decodeResource(resources, R.drawable.mc);
    }

    private String buildDateTime(Transaction transaction) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
        return LocalDateTime.parse(transaction.getDate(), DateTimeFormatter.ISO_DATE_TIME).format(formatter);
    }

    private String lastDigits(Transaction transaction) {
        String pan = transaction.getCard().getPartialPan();
        String stars = "••• ";

        return stars + pan;
    }

    private String buildAmount(Transaction transaction) {
        Integer amount = transaction.getCommittedAmount();
        return new BigDecimal(amount)
                .setScale(2, BigDecimal.ROUND_HALF_UP)
                .divide(new BigDecimal(10), BigDecimal.ROUND_HALF_UP)
                .toString()
                .concat("€");
    }

    @Override
    public int getItemCount() {
        return transactions == null ? 0 : transactions.size();
    }

}
