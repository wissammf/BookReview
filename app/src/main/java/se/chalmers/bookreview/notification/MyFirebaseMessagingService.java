package se.chalmers.bookreview.notification;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import se.chalmers.bookreview.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        try {
            JSONObject messageObject = new JSONObject(remoteMessage.getData());
            String bookId = messageObject.getString("book_id");

            Intent intent = new Intent(getString(R.string.action_refresh_book_reviews));
            intent.putExtra(getString(R.string.key_book_id), bookId);

            // Send broadcast
            sendBroadcast(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
