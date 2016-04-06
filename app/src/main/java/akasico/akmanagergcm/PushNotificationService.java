package akasico.akmanagergcm;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by matias on 19-03-16.
 */
public class PushNotificationService extends GcmListenerService {
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");

        //createNotification(mTitle, push_msg);
        Log.e("MENSAJE",message);
        Aktool.notificacion(getApplicationContext(),"sdfds",message);
    }
}
