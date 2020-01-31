/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Savelii Zagurskii
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.example.cashbox.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit.RetrofitError;
import com.example.cashbox.interfaces.OnSuggestionsListener;
import com.example.cashbox.realm.models.cache.Query;
import com.example.cashbox.realm.models.cache.Result;
import com.example.cashbox.realm.models.dadata.RealmDaDataSuggestion;
import com.example.cashbox.rest.DaDataBody;
import com.example.cashbox.rest.DaDataRestClient;
import com.example.cashbox.test.Bound;

import org.json.JSONObject;

/**
 * @author Savelii Zagurskii
 */
public class ServerUtils {
    // Executor that runs queries in a queue
    private final static ExecutorService executor = Executors.newSingleThreadExecutor();

    /**
     * Query the DaData for current query.
     *
     * @param query    your query to process.
     * @param listener listener to get callback on ready suggestions.
     */
    public static void query(final String query, final OnSuggestionsListener listener) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
                // Trim current query for ignoring whitespaces
                String queryFromUser = query.replaceAll("\\s+", " ").trim();

                // If the query is not empty, we proceed
                if (!queryFromUser.isEmpty()) {
                    // Get default instance of Realm
                    Realm realm = Realm.getDefaultInstance();

                    // Query realm for current user query.
                    // If it is cached, we will get results.
                    // Otherwise, we need to query over the Internet
                    RealmResults<Query> queryRealmResults = realm.where(Query.class).equalTo("query", queryFromUser).findAll();

                    // Initialize the list of suggestions which we will pass to listener
                    final List<String> suggestions = new ArrayList<>(10);

                    boolean success = false;

                    // If we have no cache on this query,
                    // we have to query over the Internet
                    if (queryRealmResults.size() == 0) {
                        RealmDaDataSuggestion suggestion = null;
                        try {
                            // Synchronously get the answer from DaData
                            //String from_bound = "{ \"value\": \"region\" }";
                            //String to_bound = "{ \"value\": \"region\" }";
//                            JSONObject jsonObj = new JSONObject();
//                            jsonObj.put("value","region");
//                            JSONObject jsonObj2 = new JSONObject();
//                            jsonObj.put("value","region");
                            Bound from = new Bound();
                            from.setValue("region");
                            Bound to = new Bound();
                            to.setValue("region");
                            suggestion = DaDataRestClient.getInstance().suggestSync(new DaDataBody(queryFromUser, 10, from, to));
                            Log.i("1337",suggestion.toString());
                            success = true;
                        } catch (RetrofitError e) {
                            e.printStackTrace();

                            dispatchError(e.getMessage(), listener);
                        } catch (Exception e) {
                            e.printStackTrace();

                            dispatchError(e.getMessage(), listener);
                        }

                        if (success) {
                            // Cache if success
                            cacheUserQueryWithServerResult(queryFromUser, realm, suggestions, suggestion);
                        }
                    } else {
                        // Fill from cache
                        fillSuggestionsFromCache(queryRealmResults, suggestions);
                    }

                    // Close current open Realm instance
                    realm.close();

                    // Update suggestions
                    dispatchUpdate(suggestions, listener);
                }
            }
        };
        executor.submit(runnable);
    }

    /**
     * Send callback that notifies about something went wrong.
     *
     * @param message  error message.
     * @param listener listener to get callback on error.
     */
    private static void dispatchError(final String message, final OnSuggestionsListener listener) {
        if (listener != null) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onError(message);
                }
            });
        }
    }

    /**
     * Fill a list of suggestions with data from cache.
     *
     * @param queryRealmResults query with cache results.
     * @param suggestions       list which needs to be filled.
     */
    private static void fillSuggestionsFromCache(RealmResults<Query> queryRealmResults, List<String> suggestions) {
        for (int i = 0; i < queryRealmResults.size(); i++) {
            RealmList<Result> result = queryRealmResults.get(i).getResult();

            for (int j = 0; j < result.size(); j++) {
                suggestions.add(result.get(j).getResult());
            }
        }
    }

    /**
     * Cache the DaData answer to Realm.
     *
     * @param queryFromUser trimmed user query.
     * @param realm         instance of Realm.
     * @param suggestions   list of suggestions to be filled.
     * @param suggestion    an object that corresponds the answer from DaData.
     */
    private static void cacheUserQueryWithServerResult(String queryFromUser, Realm realm, List<String> suggestions, RealmDaDataSuggestion suggestion) {
        RealmList<Result> resultsRealm = new RealmList<>();
        if (suggestion != null) {
            for (int i = 0; i < suggestion.getSuggestions().size(); i++) {
                String suggestionResult = suggestion.getSuggestions().get(i).getValue();

                suggestions.add(suggestionResult);

                realm.beginTransaction();

                Result result = realm.createObject(Result.class);
                result.setResult(suggestionResult);
                resultsRealm.add(result);

                realm.commitTransaction();
            }

//            realm.beginTransaction();
//
//            Query query = realm.createObject(Query.class);
//
//            query.setId(UUID.randomUUID().toString());
//            query.setQuery(queryFromUser);
//            query.setResult(resultsRealm);
//
//            realm.commitTransaction();
        }
    }

    /**
     * Dispatch update to UI.
     *
     * @param suggestions suggestions that need to be passed to UI.
     * @param listener    listener to get callback on ready suggestions.
     */
    private static void dispatchUpdate(final List<String> suggestions, final OnSuggestionsListener listener) {
        if (listener != null && suggestions.size() > 0) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onSuggestionsReady(suggestions);
                }
            });
        }
    }
}