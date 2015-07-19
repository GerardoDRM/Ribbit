package doge.thecraftcod.ribbit;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by gerardo on 19/07/15.
 */
public class RibbitApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "VfjkTB5LnYj46Pb38sWrwun6o5jTOJfC2iKAG3Uf", "ZwXix5SI8p93s3FlBZegLMEytb73ma6zKR3oGE51");

    }


}

