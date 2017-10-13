package tpv.cirer.com.restaurante.servicios;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by JUAN on 20/11/2015.
 */
public class AndroPHPService  extends IntentService {
    public AndroPHPService() {
        super("AndroPHPService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Do the task here
        Log.i("AndroPHPService", "Service AndroPHP running");
    }
}