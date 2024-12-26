package com.example.notificationreader;

import android.app.Notification;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NotificationListenerServiceExample extends NotificationListenerService {

    private static final String TAG = "NotificationListener";

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        // Get notification details
        Notification notification = sbn.getNotification();
        if (notification == null || notification.extras == null) {
            return;
        }

        // Extract title and text
        String title = notification.extras.getString(Notification.EXTRA_TITLE, "No Title");
        String text = notification.extras.getString(Notification.EXTRA_TEXT, "No Text");

        Log.e(TAG, "Notification Title: " + title);
        Log.e(TAG, "Notification Text: " + text);

        // Extract OTP using regex (6-digit number)
        String otp = extractOTP(text);
        if (otp != null) {
            Log.e(TAG, "Extracted OTP: " + otp);
            // You can pass the OTP to your app using a broadcast or save it for later
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.e(TAG, "Notification removed: " + sbn.getPackageName());
    }

    // Helper method to extract OTP from text using regex
    private String extractOTP(String text) {
        if (text == null) return null;
        Pattern pattern = Pattern.compile("\\b\\d{6}\\b"); // Matches 6-digit numbers
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(0); // Return the first match
        }
        return null;
    }
}
