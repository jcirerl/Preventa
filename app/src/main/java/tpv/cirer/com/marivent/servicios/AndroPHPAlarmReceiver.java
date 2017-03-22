package tpv.cirer.com.marivent.servicios;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import tpv.cirer.com.marivent.herramientas.ProcessManager;

import java.util.List;

/**
 * Created by JUAN on 20/11/2015.
 */

public class AndroPHPAlarmReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;
    public static final String ACTION = "com.cirer.aguas.principal";

    // Triggered by the Alarm periodically (starts the service to run task)
    @Override
    public void onReceive(Context context, Intent intent) {
        List<ProcessManager.Process> processes = ProcessManager.getRunningApps();
        boolean bexist = false;
        for (ProcessManager.Process process : processes) {
            if (process.name.contains("com.ayansoft.androphp/mysql/mysqld")) {
                bexist = true;
            }
        }
        if (bexist) {
            Toast.makeText(context, "AndroPHP Running", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "AndroPHP NOT Running", Toast.LENGTH_LONG).show();

        }

 //       Toast.makeText(context, "AndroPHP Running", Toast.LENGTH_LONG).show();
        Intent i = new Intent(context, AndroPHPService.class);
        i.putExtra("foo", "bar");
        context.startService(i);
    }
}