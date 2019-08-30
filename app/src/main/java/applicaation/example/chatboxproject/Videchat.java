package applicaation.example.chatboxproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Subscriber;
import com.opentok.android.OpentokError;
//import android.support.annotation.NonNull;
import android.Manifest;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


import android.os.Bundle;
import android.widget.FrameLayout;

public class Videchat extends AppCompatActivity implements  Session.SessionListener,PublisherKit.PublisherListener  {



    private static String API_KEY = "46412382";
    private static String SESSION_ID = "2_MX40NjQxMjM4Mn5-MTU2NzA3ODAwMjUyOX44MnFLSC9XWUpZcWdneWZCL3ZZWVpLTzJ-fg";
    private static String TOKEN = "T1==cGFydG5lcl9pZD00NjQxMjM4MiZzaWc9NzJlMWJhZGYwNDYwMTE1MjY0NDAyMTBkMTM5YWU3YjE5NDg5MGU1YzpzZXNzaW9uX2lkPTJfTVg0ME5qUXhNak00TW41LU1UVTJOekEzT0RBd01qVXlPWDQ0TW5GTFNDOVhXVXBaY1dkbmVXWkNMM1paV1ZwTFR6Si1mZyZjcmVhdGVfdGltZT0xNTY3MDc4MDkxJm5vbmNlPTAuMDI3MzU5ODYzODAzMjQ1MDY2JnJvbGU9cHVibGlzaGVyJmV4cGlyZV90aW1lPTE1Njk2NzAwOTEmaW5pdGlhbF9sYXlvdXRfY2xhc3NfbGlzdD0=";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int RC_SETTINGS_SCREEN_PERM = 123;
    private static final int RC_VIDEO_APP_PERM = 124;
    private Session mSession;
    private FrameLayout mPublisherViewContainer;
    private FrameLayout mSubscriberViewContainer;
    private Publisher mPublisher;
    private Subscriber mSubscriber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videchat);

        requestPermissions();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    @AfterPermissionGranted(RC_VIDEO_APP_PERM)
    private void requestPermissions() {
        String[] perms = { Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO };
        if (EasyPermissions.hasPermissions(this, perms)) {
            mPublisherViewContainer = (FrameLayout)findViewById(R.id.publisher_container);
            mSubscriberViewContainer = (FrameLayout)findViewById(R.id.subscriber_container);

            mSession = new Session.Builder(this, API_KEY, SESSION_ID).build();
            mSession.setSessionListener(this);
            mSession.connect(TOKEN);


        } else {
            EasyPermissions.requestPermissions(this, "This app needs access to your camera and mic to make video calls", RC_VIDEO_APP_PERM, perms);
        }
    }

    @Override
    public void onConnected(Session session) {

        Log.i(LOG_TAG, "Session Connected");

        mPublisher = new Publisher.Builder(this).build();
        mPublisher.setPublisherListener(this);

        mPublisherViewContainer.addView(mPublisher.getView());
        mSession.publish(mPublisher);

    }

    @Override
    public void onDisconnected(Session session) {

    }

    @Override
    public void onStreamReceived(Session session, Stream stream) {

        Log.i(LOG_TAG, "Stream Received");

        if (mSubscriber == null) {
            mSubscriber = new Subscriber.Builder(this, stream).build();
            mSession.subscribe(mSubscriber);
            mSubscriberViewContainer.addView(mSubscriber.getView());
        }

    }

    @Override
    public void onStreamDropped(Session session, Stream stream) {

        Log.i(LOG_TAG, "Stream Dropped");

        if (mSubscriber != null) {
            mSubscriber = null;
            mSubscriberViewContainer.removeAllViews();
        }

    }

    @Override
    public void onError(Session session, OpentokError opentokError) {

    }

    @Override
    public void onStreamCreated(PublisherKit publisherKit, Stream stream) {

        Log.i(LOG_TAG, "Publisher onStreamCreated");

    }

    @Override
    public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {

        Log.i(LOG_TAG, "Publisher onStreamDestroyed");

    }

    @Override
    public void onError(PublisherKit publisherKit, OpentokError opentokError) {

        Log.e(LOG_TAG, "Publisher error: " + opentokError.getMessage());

    }

}
