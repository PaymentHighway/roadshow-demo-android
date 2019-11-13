package io.paymenthighway.phroadshowdemo;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Set;

import io.paymenthighway.phroadshowdemo.Model.TokenizedCard;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ListCardsActivity extends ApiActivity {

    private Gson gson;
    private SingleObserver<Set<TokenizedCard>> observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_cards);

        initApiActivity();

        gson = new Gson();

        backButton();
        checkCancelOrFailure();
        createObserver();
        getCards();
    }

    private void backButton() {
        final Button button = findViewById(R.id.back_to_menu);
        button.setOnClickListener(v -> gotoAction(MainActivity.class));
    }

    protected void gotoAction(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }


    private void checkCancelOrFailure() {
        Intent intent = getIntent();
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            Uri uri = intent.getData();
            Log.d("PHDEMO", uri.getQuery());

            String cancel = uri.getQueryParameter("sph-cancel");
            String failure = uri.getQueryParameter("sph-failure");

            if (cancel != null) {
                createAlertDialog("Cancelled", "Add card cancelled.");
            } else if (failure != null) {
                createAlertDialog("Failed", "You failed to add card.");
            }
        }
    }

    private void createAlertDialog(String title, String message) {
        Log.d("PHDEMO", "alert dialog");
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setNeutralButton("OK", (dialog, button) -> dialog.dismiss())
                .show();
    }

    private void createObserver() {
        observer = new SingleObserver<Set<TokenizedCard>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(Set<TokenizedCard> tokenizedCards) {
                initRecyclerView(tokenizedCards);
                printCards(tokenizedCards);
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
                .subscribe(observer);
    }

    private void printCards(Set<TokenizedCard> tokenizedCards) {
        Log.d("PHDEMO", "=================================================");
        tokenizedCards.forEach((tokenizedCard) -> Log.d("PHDEMO", gson.toJson(tokenizedCard)));
        Log.d("PHDEMO", "=================================================");
    }

    private void initRecyclerView(Set<TokenizedCard> tokenizedCards) {
        RecyclerView recyclerView = findViewById(R.id.list_cards_recycler_view);
        recyclerView.addItemDecoration(new LineDividerItemDecoraton(this, R.drawable.line_divider));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        CardItemAdapter cardAdapter = new CardItemAdapter(new ArrayList<>(tokenizedCards), getResources());
        recyclerView.setAdapter(cardAdapter);

    }

}
