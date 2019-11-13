package io.paymenthighway.phroadshowdemo.Service;

import java.util.Set;

import io.paymenthighway.phroadshowdemo.Model.ChargeResponse;
import io.paymenthighway.phroadshowdemo.Model.TokenizationForm;
import io.paymenthighway.phroadshowdemo.Model.TokenizedCard;
import io.paymenthighway.phroadshowdemo.Model.Transaction;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    /* TODO some kind of authentication needed */

    @GET("cards")
    Single<Set<TokenizedCard>> getCards(@Query("user") String user);

    @GET("transactions")
    Single<Set<Transaction>> getTransactions(@Query("user") String user);

    @GET("card/add/form")
    Single<TokenizationForm> addCard(
            @Query("user") String user,
            @Query("success") String success,
            @Query("cancel") String cancel,
            @Query("failure") String failure
    );

    @GET("charge")
    Single<ChargeResponse> chargeCard(
            @Query("user") String user,
            @Query("token") String token,
            @Query("amount") Integer amount,
            @Query("order") String order,
            @Query("success") String success,
            @Query("cancel") String cancel,
            @Query("failure") String failure
    );
}
