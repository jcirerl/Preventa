package tpv.cirer.com.marivent.ui;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.os.MessageQueue;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.GlideModule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.herramientas.Filtro;
import tpv.cirer.com.marivent.herramientas.TaskHelper;
import tpv.cirer.com.marivent.modelo.Palabras;

public class SplashScreen extends AppCompatActivity {
    private static String URL_SERVER="";
    private static String URL_SERVERSWS;
    private static final String TAG_PALABRAS = "Lista Palabras";
    public static List<Palabras> lpalabras;
    private boolean ok_permisos;
    private String url_palabras;

    String TAG_SERVER = "SERVER: ";
    String TAG_FILE = "FILE: ";
    String TAG_SERVERSWS = "SERVERSWS: ";
    // Progress Dialog
    private ProgressDialog pDialogserver,pDialogserverws,pDialogPalabras,pDialogfile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        ok_permisos = false;

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        final String androidOS = Build.VERSION.RELEASE;
        Log.i("Android", androidOS + " SDK " + Integer.toString(Build.VERSION.SDK_INT) + " JELLY BEAN " + Integer.toString(Build.VERSION_CODES.JELLY_BEAN_MR1));
        Thread timerThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    /**
                     * Showing splashscreen while making network calls to download necessary
                     * data before launching the app Will use AsyncTask to make http call
                     */

                    SharedPreferences pref =
                            PreferenceManager.getDefaultSharedPreferences(
                                    SplashScreen.this);
                    Filtro.setOpurl(pref.getString("opurl", "HOSTING"));
                    Filtro.setIdioma(pref.getString("opidioma", "ESP"));
                    Filtro.setOpgrid(Integer.parseInt(pref.getString("opgrid", "8")));
                    Filtro.setOpmesas(Integer.parseInt(pref.getString("opmesas", "128")));
                    Filtro.setOptipoarticulo(Float.parseFloat(pref.getString("optipoarticulo", "16.0")));
                    Filtro.setOptoolbar(Integer.parseInt(pref.getString("optoolbar", "0")));
                    Filtro.setOptab(Integer.parseInt(pref.getString("optab", "0")));

                    Filtro.setOppedidomesa(Boolean.parseBoolean(pref.getString("oppedidomesa", "false"))); // CONTROL PEDIDOS DESDE MESA

                    Filtro.setOppedidodirectomesa(Boolean.parseBoolean(pref.getString("oppedidodirectomesa", "false")));
                    Filtro.setOpfacturadirectomesa(Boolean.parseBoolean(pref.getString("opfacturadirectomesa", "false")));

                    Filtro.setOpintervalo(Integer.parseInt(pref.getString("opintervalo", "10000")));
                    Filtro.setOplog(Boolean.parseBoolean(pref.getString("oplog", "true")));
                    Filtro.setOptab(Integer.parseInt(pref.getString("optab", "0")));
                    Filtro.setOptipotablet(Integer.parseInt(pref.getString("optipotablet", "0")));
                    Filtro.setFilelog("");
                    Log.i("oplog", Boolean.toString((Filtro.getOplog())));
                    if (Filtro.getOptoolbar() == 0) {
                        Filtro.setHide_toolbar1(false);
                        Filtro.setHide_toolbar2(false);
                    } else {
                        Filtro.setHide_toolbar1(true);
                        Filtro.setHide_toolbar2(true);
                    }
////                    Log.i("pixels",Double.toString(checkDimension(getApplicationContext())));
                    if (Filtro.getOplog()) {
                        Log.i("oplog", Boolean.toString((Filtro.getOplog())));
                        /// REGISTRAR LOG APLICACION
                        String fileName = "logcat_" + System.currentTimeMillis() + ".txt";
                        try {
                            File outputFile = new File(Environment.getExternalStoragePublicDirectory("/www/tpv/log"), fileName);
                            Filtro.setFilelog(outputFile.getAbsolutePath());
                            @SuppressWarnings("unused")
                            Process process = Runtime.getRuntime().exec("logcat -v time -f " + outputFile.getAbsolutePath() + " -r 32000 ");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    /////////////////////////////////////////////////////////////////////////////////////////////////////
                    // ABRIR FILE LOCAL O HOSTING
                    //Find the directory for the SD Card using the API
                    //*Don't* hardcode "/sdcard"
//                    File sdcard = Environment.getExternalStorageDirectory();
                    //Get the text file
//                    File file = new File(sdcard,Filtro.getOpurl()+".txt");
/////                    if (androidOS.contains("6.0.1")) {
// Here, thisActivity is the current activity

/*                        try {
                            HttpURLConnection.setFollowRedirects(false);
                            // note : you may also need
                            //HttpURLConnection.setInstanceFollowRedirects(false)
                            URL_SERVER = "http://localhost:8080/tpv/get_host_" + Filtro.getOpurl().trim().toLowerCase() + ".php";

                            HttpURLConnection con = (HttpURLConnection) new URL(URL_SERVER).openConnection();
                            con.setRequestMethod("HEAD");
                            if ((con.getResponseCode() == HttpURLConnection.HTTP_OK)) {
                                Log.d("FILE_EXISTS", "true");
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        // runs on UI thread
                                        new GetFile().execute(URL_SERVER);
                                    }
                                });
                            } else {
                                Log.d("FILE_EXISTS", "false " + Integer.toString(con.getResponseCode()));
                                showToastInThread(SplashScreen.this, "FILE_EXISTS " + URL_SERVER + " false " + Integer.toString(con.getResponseCode()));
                                finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("FILE_EXISTS", "false");
                            showToastInThread(SplashScreen.this, "URL SERVER NO EXISTE GETFILE " + URL_SERVER);
                            finish();
                        }
*/
/////                    } else {
                        File file = new File(Environment.getExternalStoragePublicDirectory("/www/tpv/"), Filtro.getOpurl() + ".txt");
                        if (!file.exists()) {
                            Log.e("Fichero", "File not found " + file.getAbsolutePath());
                        }


                        //Read text from file
                        StringBuilder text = new StringBuilder();

                        try {
                            BufferedReader br = new BufferedReader(new FileReader(file));
                            String line;

                            while ((line = br.readLine()) != null) {
                                text.append(line);
                                break; // solo debe leer una linaa.
//                            text.append('\n');
                            }
                            br.close();
                            URL_SERVER = String.valueOf(text).trim() + "/get_server.php";
                        } catch (IOException e) {
                            //You'll need to add proper error handling here
                            showToastInThread(SplashScreen.this, "NO ENCONTRADO FILE " + file.getAbsolutePath());
                            URL_SERVER = String.valueOf(text).trim() + "/get_server.php";
                            finish();
                        }
/////                    }
                    Log.i("URLSERVER", URL_SERVER);
///                    URL_SERVER = "http://192.168.1.33:8080/tpv/get_server.php";
                    ////////////////////////////////////////////////////////////////////////////////////////////
                    try {
                        HttpURLConnection.setFollowRedirects(false);
                        // note : you may also need
                        //HttpURLConnection.setInstanceFollowRedirects(false)

                        HttpURLConnection con = (HttpURLConnection) new URL(URL_SERVER).openConnection();
                        con.setRequestMethod("HEAD");
                        if ((con.getResponseCode() == HttpURLConnection.HTTP_OK)) {
                            Log.d("FILE_EXISTS", "true");
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    // runs on UI thread
                                    new GetServer().execute(URL_SERVER);
                                    if (Filtro.getOpurl().contains("LOCAL")) {
                                        Filtro.setConexion("LOCAL");
                                    } else {
                                        Filtro.setConexion("HOSTING");
                                    }
                                }
                            });

                        } else {
                            Log.d("FILE_EXISTS", "false " + Integer.toString(con.getResponseCode()));
                            showToastInThread(SplashScreen.this, "FILE_EXISTS false " + Integer.toString(con.getResponseCode()));
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("FILE_EXISTS", "false");
                        showToastInThread(SplashScreen.this, "URL SERVER NO EXISTE " + URL_SERVER);
                        finish();
                    }
                }
            }
        };
        timerThread.start();
    }
    private double checkDimension(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        int dens=dm.densityDpi;
        double wi=(double)width/(double)dens;
        double hi=(double)height/(double)dens;
        double x = Math.pow(wi,2);
        double y = Math.pow(hi,2);
        double screenInches = Math.sqrt(x+y);
        return screenInches;
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
     public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
    public void showToastInThread(final Context context,final String str){
        Looper.prepare();
        MessageQueue queue = Looper.myQueue();
        queue.addIdleHandler(new MessageQueue.IdleHandler() {
            int mReqCount = 0;

            @Override
            public boolean queueIdle() {
                if (++mReqCount == 2) {
                    Looper.myLooper().quit();
                    return false;
                } else
                    return true;
            }
        });
        Toast.makeText(context, str,Toast.LENGTH_SHORT).show();
        Looper.loop();
    }
    /**
     * Adding spinner data grupo
     */
    private void populateServersws() {
        try {
            HttpURLConnection.setFollowRedirects(false);
            // note : you may also need
            //HttpURLConnection.setInstanceFollowRedirects(false)

            HttpURLConnection con =  (HttpURLConnection) new URL(Filtro.getUrl()).openConnection();
            con.setRequestMethod("HEAD");
            if( (con.getResponseCode() == HttpURLConnection.HTTP_OK) ) {
                Log.d("FILE_EXISTS", "true");

            }else {
                Log.d("FILE_EXISTS", "false " + Integer.toString(con.getResponseCode()));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.d("FILE_EXISTS", "false");
            showToastInThread(SplashScreen.this,"URL NO EXISTE");
            finish();
        }
        if ((Filtro.getConexion().contains("HOSTING") && isOnline()) || (Filtro.getConexion().contains("LOCAL")) ) {
                        Intent i = new Intent(SplashScreen.this, LoginActivity.class);
//            Intent i = new Intent(SplashScreen.this, ActividadPrincipal.class);
            startActivity(i);
        }else{
/*                        Looper.prepare();
                        Toast.makeText(SplashScreen.this,
                                "NO HAY CONEXION A INTERNET",
                                Toast.LENGTH_LONG).show();
                        Looper.loop();
*/
            showToastInThread(SplashScreen.this,"NO HAY CONEXION A INTERNET");
            finish();
        }

        URL_SERVERSWS = Filtro.getUrl()+"/get_serversws.php";
        new GetServersws().execute(URL_SERVERSWS);

        // RELLENAMOS PALABRAS IDIOMA
        url_palabras = Filtro.getUrl() + "/RellenaListaPalabras.php";
        Log.i("Url Palabras",url_palabras);
        lpalabras = new ArrayList<Palabras>();
        TaskHelper.execute(new GetPalabras(), url_palabras);

    }
    public class GetFile extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogfile = new ProgressDialog(SplashScreen.this);
            pDialogfile.setMessage(getPalabras("Cargando")+" URL. "+getPalabras("Espere por favor")+"...");
            pDialogfile.setIndeterminate(false);
            pDialogfile.setCancelable(true);
            pDialogfile.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                // for Get request
                ///           urlConnection.setRequestMethod("GET");

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
//                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
//                params1.add(new BasicNameValuePair("filtro", cSql));

                ContentValues values = new ContentValues();
                values.put("filtro", "");

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getQuery(params1));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON-->", response.toString());
                    if (response.toString().trim().length()==0){
                        showToastInThread(SplashScreen.this,"Sin Datos en "+URL_SERVER+response.toString());
                        showToastInThread(SplashScreen.this,response.toString());
                        result = 0;
                    }else {
                        parseResultFile(response.toString());
                        result = 1; // Successful
                    }
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */
            pDialogfile.dismiss();
            if (result == 1) {
                Log.e(TAG_FILE, "OK FILE");
            } else {
                Log.e(TAG_FILE, "Failed to fetch data!");
                finish();
            }
        }
    }
    private void parseResultFile(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                URL_SERVER = post.optString("URL").trim() + "/get_server.php";

                Log.i("url SERVER", URL_SERVER);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Async task to get all food categories
     */
    public class GetServer extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogserver = new ProgressDialog(SplashScreen.this);
            pDialogserver.setMessage(getPalabras("Cargando")+" Server. "+getPalabras("Espere por favor")+"...");
            pDialogserver.setIndeterminate(false);
            pDialogserver.setCancelable(true);
            pDialogserver.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";

            xWhere += " WHERE server.SERVER='"+Filtro.getOpurl()+"'";

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Lista",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                // for Get request
                ///           urlConnection.setRequestMethod("GET");

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
//                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
//                params1.add(new BasicNameValuePair("filtro", cSql));

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getQuery(params1));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON-->", response.toString());
                    if (response.toString().contains("Warning")){
                        showToastInThread(SplashScreen.this,response.toString());
                        result = 0;
                    }else {
                        parseResultServer(response.toString());
                        result = 1; // Successful
                    }
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */
            pDialogserver.dismiss();
            if (result == 1) {
                Log.e(TAG_SERVER, "OK SERVER");
                populateServersws();
            } else {
                Log.e(TAG_SERVER, "Failed to fetch data!");
                finish();
            }
        }
    }
    private void parseResultServer(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                Filtro.setUrl(post.optString("URL").trim());
                Filtro.setIp_url(post.optString("IP_URL").trim());

                Log.i("url ", Filtro.getUrl()+" "+Filtro.getIp_url());

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /**
     * Async task to get all food categories
     */
    public class GetServersws extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogserverws = new ProgressDialog(SplashScreen.this);
            pDialogserverws.setMessage(getPalabras("Cargando")+" Server WS...");
            pDialogserverws.setIndeterminate(false);
            pDialogserverws.setCancelable(true);
            pDialogserverws.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";

            xWhere += " WHERE serversws.SERVER='"+Filtro.getOpurl()+"'";

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Lista",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                // for Get request
                ///           urlConnection.setRequestMethod("GET");

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
//                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
//                params1.add(new BasicNameValuePair("filtro", cSql));

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getQuery(params1));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON-->", response.toString());

                    parseResultServersws(response.toString());
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */
            pDialogserverws.dismiss();
            if (result == 1) {
                Log.e(TAG_SERVERSWS, "OK SERVER WS");
            } else {
                Log.e(TAG_SERVERSWS, "Failed to fetch data!");
            }
        }
    }
    private void parseResultServersws(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                Filtro.setNamespace(post.optString("NAMESPACE").trim());
                Filtro.setDirreportname(post.optString("DIRREPORTNAME").trim());
                Filtro.setDirpdfname(post.optString("DIRPDFNAME").trim());
                Filtro.setDriver(post.optString("DRIVER").trim());
                Filtro.setHost(post.optString("HOST").trim());
                Filtro.setDbname(post.optString("DBNAME").trim());
                Filtro.setUsername(post.optString("USERNAME").trim());
                Filtro.setPwdname(post.optString("PWDNAME").trim());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //   private String getQuery(List<NameValuePair> params1) throws UnsupportedEncodingException
    private String getQuery(ContentValues values) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, Object> entry : values.valueSet()) {
            String key = entry.getKey();
            String value = entry.getValue().toString();
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value, "UTF-8"));
        }
        Log.i("Result QUERY", result.toString());

        return result.toString();
    }
    public class GetPalabras extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogPalabras = new ProgressDialog(SplashScreen.this);
            pDialogPalabras.setMessage("Cargando Palabras. Espere por favor...");
            pDialogPalabras.setIndeterminate(false);
            pDialogPalabras.setCancelable(true);
            pDialogPalabras.show();

        }

        @Override
        protected Integer doInBackground(String... params) {
            String cSql = "";
            String xWhere = "";
            if(!(Filtro.getIdioma().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE palabras.IDIOMA='" + Filtro.getIdioma() + "'";
                } else {
                    xWhere += " AND palabras.IDIOMA='" + Filtro.getIdioma() + "'";
                }
            }

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Lista",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON-->", response.toString());
                    for (Iterator<Palabras> it = lpalabras.iterator(); it.hasNext();){
                        Palabras palabras = it.next();
                        it.remove();
                    }

                    parseResultpalabras(response.toString());
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */
            pDialogPalabras.dismiss();
            if (result == 1) {
                Log.i(TAG_PALABRAS, Integer.toString(lpalabras.size()));
            } else {
                Log.e(TAG_PALABRAS, "Failed to fetch data!");
            }
        }
    }

    private void parseResultpalabras(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                Palabras cat = new Palabras(post.optInt("ID"),
                        post.optString("IDIOMA"),
                        post.optString("CLAVESTRING").trim(),
                        post.optString("PALABRA").trim());
                Log.i(TAG_PALABRAS,Integer.toString(post.optInt("ID"))+","+post.optString("IDIOMA")+","+post.optString("CLAVESTRING")+","+post.optString("PALABRA"));
                lpalabras.add(cat);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void printPermissionInfo(Context context) throws Exception {
        PackageManager pm = context.getPackageManager();
        List<PermissionGroupInfo> groupList = pm.getAllPermissionGroups(0);
        groupList.add(null); // ungrouped permissions
        Method protectionToString = PermissionInfo.class.getDeclaredMethod("protectionToString",
                int.class);
        for (PermissionGroupInfo group : groupList) {
            try {
                String name = group == null ? null : group.name;
                List<PermissionInfo> permissionInfos = pm.queryPermissionsByGroup(name, 0);
                for (PermissionInfo permissionInfo : permissionInfos) {
                    String protection = (String) protectionToString.invoke(permissionInfo,
                            permissionInfo.protectionLevel);
                    System.out.println(permissionInfo.name + " " + protection);
                }
            } catch (PackageManager.NameNotFoundException ignored) {
            }
        }
    }
    public static String getPalabras (String search){
        Log.i("Palabra Valor: ",search);
        switch (search){
            case "Cargando":
                switch (Filtro.getIdioma()) {
                    case "GBR":
                       return "Loading";
                    case "ESP":
                        return "Cargando";
                }
                break;
            case "Espere por favor":
                switch (Filtro.getIdioma()) {
                    case "GBR":
                        return "Please wait";
                    case "ESP":
                        return "Espere por favor";
                }
                break;
        }
         return "**";
    }
    public class GlideConfiguration implements GlideModule {

        @Override
        public void applyOptions(Context context, GlideBuilder builder) {
            // Apply options to the builder here.
            builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        }

        @Override
        public void registerComponents(Context context, Glide glide) {
            // register ModelLoaders here.
        }
    }
}
 
