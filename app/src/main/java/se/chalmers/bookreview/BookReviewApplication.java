package se.chalmers.bookreview;

import android.app.Application;

import com.google.firebase.messaging.FirebaseMessaging;

import se.chalmers.bookreview.net.WebRequestManager;

public class BookReviewApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        WebRequestManager.getInstance().initialize(this);

        FirebaseMessaging.getInstance().subscribeToTopic("newReview");
    }
}
