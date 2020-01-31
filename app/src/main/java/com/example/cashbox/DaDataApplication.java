package com.example.cashbox;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import com.example.cashbox.realm.modules.QueryRealmModule;

public class DaDataApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .modules(new QueryRealmModule())
                .name("queries.realm")
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);
    }
}
