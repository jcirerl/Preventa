package tpv.cirer.com.restaurante.herramientas;

import android.app.Activity;
import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by JUAN on 08/10/2016.
 */


public abstract class SerialExecutor {
    private final ExecutorService mExecutorService;

    public SerialExecutor() {
        mExecutorService = Executors.newFixedThreadPool(1);
    }

    public void queue(Context context, TaskParams params) {
        mExecutorService.submit(new SerialTask(context, params));
    }

    public void stop() {
        mExecutorService.shutdown();
    }

    public abstract void execute(TaskParams params);

    public static abstract class TaskParams { }

    private class SerialTask implements Runnable {
        private final Context mContext;
        private final TaskParams mParams;

        public SerialTask(Context context, TaskParams params) {
            mContext = context;
            mParams = params;
        }

        public void run() {
            execute(mParams);
            Activity a = (Activity) mContext;
            a.runOnUiThread(new OnPostExecute());
        }
    }

    /**
     * Used to notify the UI thread
     */
    private class OnPostExecute implements Runnable {

        public void run() {

        }
    }
}