package io.paymenthighway.phroadshowdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Set;

import io.paymenthighway.phroadshowdemo.Model.ChargeResponse;
import io.paymenthighway.phroadshowdemo.Model.TokenizedCard;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ChargeCardActivity extends ApiActivity {

    private SingleObserver<ChargeResponse> chargeObserver;
    private SingleObserver<Set<TokenizedCard>> cardsObserver;
    private HorizontalCardItemAdapter cardAdapter;
    private Integer amount;
    private String order;
    private String token;

    private TextView mAmount;
    private Toast sendingTransactionToast;

    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_card);

        initApiActivity();

        gson = new Gson();

        /* dummy sample values */
        amount = 100;
        order = "123ABC";

        initView();
        createObservers();
        getCards();
        addChargeButton();
    }

    private void findViews() {
        mAmount = findViewById(R.id.charge_amount);
    }

    private void initView() {
        findViews();

        mAmount.setText(buildAmount(amount));
    }

    private String buildAmount(Integer amount) {
        return new BigDecimal(amount)
                .setScale(2, BigDecimal.ROUND_HALF_UP)
                .divide(new BigDecimal(10), BigDecimal.ROUND_HALF_UP)
                .toString()
                .concat("€");
    }

    private void addChargeButton() {
        final Button button = findViewById(R.id.pay);
        button.setOnClickListener(v -> chargeCard());
    }

    private void createObservers() {
        createCardsObserver();
        createChargeObserver();
    }

    private void createChargeObserver() {
        chargeObserver = new SingleObserver<ChargeResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(ChargeResponse response) {
                sendingTransactionToast.cancel();
                Log.d("PHDEMO", "=================================================");
                Log.d("PHDEMO", response.getResponseCode());
                Log.d("PHDEMO", "=================================================");
                if (response.getResponseCode().equals("100")) {
                    gotoMain();
                } else if (response.getResponseCode().equals("400")) {
                    view3DSForm(response.getThreeDSecureUrl());
                } else {
                    Log.d("PHDEMO", "=================================================");
                    Log.d("PHDEMO", "Bad response code");
                    Log.d("PHDEMO", "=================================================");
                    gotoMain();
                }

            }

            @Override
            public void onError(Throwable e) {
                printError(e);
            }
        };
    }

    private void view3DSForm(String url) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    private void gotoMain() {
        Toast.makeText(this, "Transaction successful", Toast.LENGTH_LONG).show();
        gotoAction(MainActivity.class);
    }

    private void chargeCard() {
        if (cardAdapter.getSelectedToken() == null) {
            Toast.makeText(this, "Please select card first", Toast.LENGTH_SHORT).show();
        } else {
            token = cardAdapter.getSelectedToken().toString();
            callChargeApi();
        }
    }

    private void callChargeApi() {
        sendingTransactionToast = Toast.makeText(this, "Sending transaction", Toast.LENGTH_LONG);
        sendingTransactionToast.show();
        String success = properties.getProperty("return.charge_card.success");
        String cancel = properties.getProperty("return.charge_card.success");
        String failure = properties.getProperty("return.charge_card.success");
        apiService.chargeCard(user, token, amount, order, success, cancel, failure)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(chargeObserver);

    }

    private void createCardsObserver() {
        cardsObserver = new SingleObserver<Set<TokenizedCard>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(Set<TokenizedCard> tokenizedCards) {
                printCards(tokenizedCards);
                initRecyclerView(tokenizedCards);
            }

            @Override
            public void onError(Throwable e) {
                printError(e);
            }
        };
    }

    private void getCards() {
        apiService.getCards(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cardsObserver);
    }

    private void printCards(Set<TokenizedCard> tokenizedCards) {
        Log.d("PHDEMO", "=================================================");
        tokenizedCards.forEach((tokenizedCard) -> Log.d("PHDEMO", gson.toJson(tokenizedCard)));
        Log.d("PHDEMO", "=================================================");
    }

    private void initRecyclerView(Set<TokenizedCard> tokenizedCards) {
        RecyclerView recyclerView = findViewById(R.id.charge_cards_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        cardAdapter = new HorizontalCardItemAdapter(new ArrayList<>(tokenizedCards), getResources(), this);
        recyclerView.setAdapter(cardAdapter);

    }
}
