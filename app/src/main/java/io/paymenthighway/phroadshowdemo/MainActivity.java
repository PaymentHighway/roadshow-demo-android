package io.paymenthighway.phroadshowdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.widget.Button;

import io.paymenthighway.phroadshowdemo.Model.TokenizationForm;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends ApiActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initApiActivity();

        bindButtons();
    }

    private void bindButtons() {
        addCardButton();
        bindGotoButton(R.id.list_cards, ListCardsActivity.class);
        bindGotoButton(R.id.list_transactions, ListTransactionsActivity.class);
        bindGotoButton(R.id.charge, ChargeCardActivity.class);
    }

    private void addCardButton() {
        final Button button = findViewById(R.id.add_card);
        button.setOnClickListener(v -> addCard());
    }

    private void bindGotoButton(@IdRes int id, Class<?> action) {
        final Button button = findViewById(id);
        button.setOnClickListener( v -> gotoAction(action));
    }


    private void addCard() {
        Log.d("PHDEMO", "Add card");
        Log.d("PHDEMO", properties.getProperty("return.add_card.success"));
        apiService.addCard(
                user,
                properties.getProperty("return.add_card.success"),
                properties.getProperty("return.add_card.success"),
                properties.getProperty("return.add_card.success")
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<TokenizationForm>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(TokenizationForm tokenizationForm) {
                        viewAddCardForm(tokenizationForm);
                    }

                    @Override
                    public void onError(Throwable e) {
                        printError(e);
                    }
                });
    }

    private void viewAddCardForm(TokenizationForm tokenizationForm) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("url", tokenizationForm.getUrl());
        startActivity(intent);
    }

}
