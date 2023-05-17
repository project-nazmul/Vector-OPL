package com.opl.pharmavector.service;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.opl.pharmavector.activity.MainActivity;
import com.opl.pharmavector.activity.MessageShowActivity;
import com.opl.pharmavector.app.Config;
import com.opl.pharmavector.util.NotificationUtils;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DateFormat;
import java.util.Date;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private static final String TITLE = "title";
    private static final String EMPTY = "";
    private static final String MESSAGE = "message";
    private static final String IMAGE = "image";
    private static final String ACTION = "action";
    private static final String DATA = "data";
    private static final String ACTION_DESTINATION = "action_destination";
    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
    /* Map<String, String> params = remoteMessage.getData();
        String jsonStr = params.get("data");
        Log.i(MainActivity.TAG, "FireBase -> JSON: " + jsonStr);
            JSONObject jsonObject = new JSONObject(jsonStr);
            final int id = jsonObject.getInt("id");
            final String title = jsonObject.getString("title");
            final String description = jsonObject.getString("description");
            final String image = jsonObject.getString("imageUrl"); */
        if (remoteMessage.getNotification() == null) {
            remoteMessage.getData();
        }
        handleNotification(remoteMessage.getNotification().getBody());

        if (remoteMessage.getNotification() != null) {
            handleNotification(remoteMessage.getNotification().getBody());
        }

        if (remoteMessage.getData().size() > 0) {
            try {
                JSONObject json = new JSONObject(remoteMessage.getData());
                handleDataMessage(json);
            } catch (Exception ignored) {

            }
        }
    }

    public void onNewToken(String s) {
        sendRegistrationToServer(s);
    }

    private void sendRegistrationToServer(String s) {}

    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            //app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
            //play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        }
    }

    private void handleDataMessage(JSONObject json) {
        try {
            JSONObject data = new JSONObject(json.toString());
            String title = data.getString("title");
            String message = data.getString("message");
            boolean isBackground = false;
            String timestamp = DateFormat.getDateTimeInstance().format(new Date());
            String imageUrl = data.getString("image");
            Intent resultIntent = new Intent(getApplicationContext(), MessageShowActivity.class);
            resultIntent.putExtra("title", title);
            resultIntent.putExtra("timestamp", timestamp);
            resultIntent.putExtra("article_data", message);
            resultIntent.putExtra("image", imageUrl);

            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                resultIntent.putExtra("message", message);

                if (TextUtils.isEmpty(imageUrl)) {
                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                } else {
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                }
                resultIntent.putExtra("message", message);

                //app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
                //play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
                Intent resultIntent1 = new Intent(getApplicationContext(), MainActivity.class);
                resultIntent1.putExtra("message", message);

                if (TextUtils.isEmpty(imageUrl)) {
                    //showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                } else {
                    //image is present, show notification with image
                    //showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                }
            } else {
                resultIntent.putExtra("message", message);
                //check for image attachment
                if (TextUtils.isEmpty(imageUrl)) {
                    //showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                } else {
                    //image is present, show notification with image
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                    //showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                }
                //Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                resultIntent.putExtra("message", message);
                //app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
                //play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
                Intent resultIntent1 = new Intent(getApplicationContext(), MainActivity.class);
                resultIntent1.putExtra("message", message);

                if (TextUtils.isEmpty(imageUrl)) {
                    //showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                } else {
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     **/
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     **/
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}
