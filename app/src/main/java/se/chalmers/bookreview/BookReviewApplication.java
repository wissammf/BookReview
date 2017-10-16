package se.chalmers.bookreview;

import android.app.Application;

import se.chalmers.bookreview.data.WebRequestManager;

public class BookReviewApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        WebRequestManager.getInstance().initialize(this);
    }
}