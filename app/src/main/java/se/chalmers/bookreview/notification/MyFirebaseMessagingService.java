package se.chalmers.bookreview.notification;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import se.chalmers.bookreview.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Intent intent = new Intent(getString(R.string.action_refresh_book_reviews));
        intent.putExtra(getString(R.string.key_book_id), remoteMessage.getNotification().getBody());

        // Send broadcast
        sendBroadcast(intent);
    }
}
