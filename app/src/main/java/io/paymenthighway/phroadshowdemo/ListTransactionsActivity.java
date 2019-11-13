package io.paymenthighway.phroadshowdemo;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import io.paymenthighway.phroadshowdemo.Model.Transaction;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ListTransactionsActivity extends ApiActivity {

    private SingleObserver<Set<Transaction>> observer;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_transactions);

        initApiActivity();

        gson = new Gson();

        createObserver();
        getTransactions();
    }


    private void createObserver() {
        observer = new SingleObserver<Set<Transaction>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(Set<Transaction> transactions) {
                printTransactions(transactions);
                initRecyclerView(transactions);
            }

            @Override
            public void onError(Throwable e) {
                printError(e);
            }
        };
    }

    private void initRecyclerView(Set<Transaction> transactions) {
        RecyclerView recyclerView = findViewById(R.id.list_transactions_recycler_view);
        recyclerView.addItemDecoration(new LineDividerItemDecoraton(this, R.drawable.line_divider));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        TransactionItemAdapter transactionItemAdapter = new TransactionItemAdapter(setToSortedList(transactions), getResources());
        recyclerView.setAdapter(transactionItemAdapter);

    }

    private ArrayList<Transaction> setToSortedList(Set<Transaction> transactions) {
        ArrayList<Transaction> transactionsList = new ArrayList<>(transactions);
        Collections.sort(transactionsList);
        return transactionsList;
    }

    private void getTransactions() {
        apiService.getTransactions(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void printTransactions(Set<Transaction> transactions) {
        Log.d("PHDEMO", "=================================================");
        transactions.forEach((transaction) -> Log.d("PHDEMO", gson.toJson(transaction)));
        Log.d("PHDEMO", "=================================================");
    }

}
