package com.mollyiv.chatheads;

/**
 * Created by abed on 11/8/16.
 */

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class MyAccessibilityService extends AccessibilityService {

    private final String TAG = getClass().getName();

    @Override
    public void onInterrupt() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onServiceConnected() {
        Log.d(TAG, "onServiceConnected: ");
        // Set the type of events that this service wants to listen to. Others won't be passed to this service.
        // We are only considering windows state changed event.
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_WINDOWS_CHANGED | AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED| AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED;
        // If you only want this service to work with specific applications, set their package names here. Otherwise, when the service is activated, it will listen to events from all applications.
        info.packageNames = new String[]{"com.contextlogic.wish",};
        // Set the type of feedback your service will provide. We are setting it to GENERIC.
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        // Default services are invoked only if no package-specific ones are present for the type of AccessibilityEvent generated.
        // This is a general-purpose service, so we will set some flags
        info.flags = AccessibilityServiceInfo.DEFAULT | AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS | AccessibilityServiceInfo.FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY | AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS;
        // We are keeping the timeout to 0 as we don’t need any delay or to pause our accessibility events
        info.notificationTimeout = 0;
        this.setServiceInfo(info);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
//        Log.d(TAG, "onAccessibilityEvent: " + event.toString());

        AccessibilityNodeInfo source = event.getSource();
        if (source == null) {
            return;
        }
//        Log.d("Event:: ", event.toString());
//        Log.d("Source:: ", source.toString());
        traverseView(source);
//        if (source.getText() != null)
//            Log.d("Text:: ", source.getText().toString());
    }

    private void traverseView(AccessibilityNodeInfo info) {
        for (int i = 0; i < info.getChildCount(); i++) {
            if (info.getChild(i) != null) {
//                Log.d("traverse:: ", info.getChild(i).getClassName().toString());
                if (info.getChild(i).getText() != null)
                    Log.d("traverse text:: ", info.getChild(i).getText().toString());
                traverseView(info.getChild(i));
            }
        }
    }
}