package tpv.cirer.com.marivent.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.conexion_http_post.JSONParser;
import tpv.cirer.com.marivent.herramientas.Filtro;
import tpv.cirer.com.marivent.herramientas.ProcessManager;
import tpv.cirer.com.marivent.modelo.Cruge;
import tpv.cirer.com.marivent.modelo.Palabras;

import static tpv.cirer.com.marivent.ui.SplashScreen.lpalabras;

//import tpv.cirer.com.marivent.R;

public class LoginActivity extends AppCompatActivity {
    public static List<Cruge> lcruge;
    private static final String TAG_CRUGE = "Lista Cruge";
    EditText user;
    EditText pass;
    TextView lblUser;
    TextView lblPass;

    Button validar;
    Button salir;

    // declare the dialog as a member field of your activity
    ProgressDialog mProgressDialog,pDialogCruge;
    StringBuilder sb_local = new StringBuilder();
    StringBuilder response = null;
    String sourceUrl;
    String filename;
    String desDirectory;
    // Progress Dialog
    private ProgressDialog pDialog;
    // Progress dialog type (0 - for Horizontal progress bar)
    public static final int progress_bar_type = 0;

    // File url to download
    private static String file_url = "http://seas-imagemaps.besaba.com/android/tpv/";

    static Properties props;

    private String sFecha = "1970-01-01";
    JSONParser jsonParser = new JSONParser();
    private static final String url_crea_db = Filtro.getUrl() + "/crea_DB.php";
    private static final String url_create_tablas = Filtro.getUrl() + "/crea_tablas.php";
    private static final String url_update_tablas = Filtro.getUrl() + "/tablas_server_slave_new.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_FECHA = "fecha_update";

    //private static String urlServidor = "http://seas-imagemaps.besaba.com/android/tpv/controlusuario.php";
    private static String urlServidor = Filtro.getUrl() + "/controlusuario.php";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

        ////       LoginActivity.this.requestWindowFeature(Window.FEATURE_LEFT_ICON);
        setContentView(R.layout.control_login);
////        getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.marivent);
  /*  this.titulo.setText("Login");
    this.icon.setImageResource(R.drawable.loginandroid);
    this.conexion.setText(Filtro.getConexion());
    this.abonados.setText(Filtro.getAbonados());
    this.zona.setText(Filtro.getZona());
 */
        if (Filtro.getConexion().contentEquals("LOCAL")) {
            // Comprobar si aplicacion AndroPHP esta instalado
            //Put the package name here...
////            boolean installed = appInstalledOrNot("com.ayansoft.androphp");
            boolean installed = true;
            if (installed) {
                String PATH = Environment.getExternalStorageDirectory() + "/www/tpv/";
                String PATH1 = Environment.getExternalStorageDirectory() + "/www/tpv/sql/";

                sb_local.append("Procesos ejecutados:\n");
                File targetDir = new File(PATH);
                if (!targetDir.exists()) {
                    targetDir.mkdirs();
                    sb_local.append('\n').append("Creacion PATH " + PATH);

                    File targetDir1 = new File(PATH1);
                    if (!targetDir1.exists()) {
                        targetDir1.mkdirs();
                        sb_local.append('\n').append("Creacion PATH " + PATH1);
                    }

                    if (startFTP("")) {
                        ///               Toast.makeText(getBaseContext(), "SCRIPTS PHP is already installed on your phone", Toast.LENGTH_SHORT).show();
                        sb_local.append('\n').append("SCRIPTS PHP is already installed on your phone");

                        new CreaDB().execute();
                    } else {
                        ///                  Toast.makeText(getBaseContext(), "SCRIPTS PHP NOT IS installed on your phone", Toast.LENGTH_SHORT).show();
                        sb_local.append('\n').append("SCRIPTS PHP NOT is installed on your phone");
                    }

                    new AlertDialog.Builder(LoginActivity.this).setMessage(sb_local.toString()).show();

                }


                //  Si AndroPHP Instalado comprueba si esta ejecutando
                new AsyncTask<Void, Void, List<ProcessManager.Process>>() {

                    long startTime;

                    @Override
                    protected List<ProcessManager.Process> doInBackground(Void... params) {
                        startTime = System.currentTimeMillis();
                        return ProcessManager.getRunningApps();
                    }

                    @Override
                    protected void onPostExecute(List<ProcessManager.Process> processes) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Execution time: ").append(System.currentTimeMillis() - startTime).append("ms\n");
                        sb.append("Running apps:\n");
                        for (ProcessManager.Process process : processes) {
                            Log.i("SB-", process.name);
                                // Process DROIDPHP "com.github.DroidPHP"
                                // Process PALAPA "com.alfanla.android.pws"
                            if (process.name.contains("com.alfanla.android.pws")) { //Añadido
                                sb.append('\n').append(process.name);
                            } //Añadido
                        }
                        Log.i("SB", Integer.toString(sb.length()));
                        if (sb.length() < 58) { //AÑADIDO
//                                new AlertDialog.Builder(LoginActivity.this).setMessage(sb.toString()).show();
                            AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this/*,R.style.MyAlertDialogStyle*/);
                            dialog.setTitle("Confirmacion Palapa Web Server");
                            dialog.setMessage("Desea Iniciar Aplicación Palapa?");
                            dialog.setIcon(R.drawable.ok);
                            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        Intent LaunchIntent = getPackageManager()
                                                .getLaunchIntentForPackage("com.alfanla.android.pws");
                                        startActivity(LaunchIntent);
                                    } catch (ClassCastException exception) {
                                        // do something
                                    }
                                    dialog.cancel();
                                }
                            });
                            dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            dialog.show();

/*
                        new AlertDialog.Builder(LoginActivity.this).setMessage("Debe Iniciar Aplicación AndroPHP").show(); //AÑADIDO
                        // Si AndroPHP se esta EJECUTANDO pero no esta INICIADO INTENTA INICIARLO
                        //This intent will help you to launch if the package is already installed
                        Intent LaunchIntent = getPackageManager()
                                .getLaunchIntentForPackage("com.ayansoft.androphp");
                        startActivity(LaunchIntent);
                        Toast.makeText(getBaseContext(), "App AndroPHP is already installed on your phone", Toast.LENGTH_SHORT).show();
*/
                        } //AÑADIDO
                    }
                }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }

        }
        lblUser = (TextView) findViewById(R.id.LblUsuario);
        lblUser.setText(ValorCampo(R.id.LblUsuario, lblUser.getClass().getName()));

        lblPass = (TextView) findViewById(R.id.LblPassword);
        lblPass.setText(ValorCampo(R.id.LblPassword, lblPass.getClass().getName()));

        user = (EditText) findViewById(R.id.TxtUsuario);
        pass = (EditText) findViewById(R.id.TxtPassword);

        validar = (Button) findViewById(R.id.BtnAceptar);
        validar.setText(ValorCampo(R.id.BtnAceptar, validar.getClass().getName()));

        salir = (Button) findViewById(R.id.BtnSalir);
        salir.setText(ValorCampo(R.id.BtnSalir, salir.getClass().getName()));

        Log.i("url ", Filtro.getUrl());
        Log.i("urlServidor ", urlServidor);
        salir.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        validar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                new GetUsuario().execute(urlServidor,user.getText().toString(),md5(pass.getText().toString()));
                String cSql = "";
                String xWhere = "";
                xWhere = " WHERE usuarios.username='"+user.getText().toString()+"' AND usuarios.password='"+md5(pass.getText().toString())+"'";
                cSql += xWhere;
                if(cSql.equals("")) {
                    cSql="Todos";
                }
                Log.i("Sql Lista",cSql);
                Integer result = 0;
                HttpURLConnection urlConnection = null;

                try {
                    // forming th java.net.URL object
                    URL url = new URL(urlServidor);

                    urlConnection = (HttpURLConnection) url.openConnection();

                    // for Get request
                    ///           urlConnection.setRequestMethod("GET");

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
                        response = new StringBuilder();
                        String line;
                        while ((line = r.readLine()) != null) {
                            response.append(line);
                        }
                        Log.i("JSON-->", response.toString());
                        String xResult = response.toString();
                        try {
                            JSONObject json = new JSONObject(xResult);
                            JSONArray posts = json.optJSONArray("posts");

                            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
                            for (int ii = 0; ii < posts.length(); ii++) {
                                JSONObject post = posts.optJSONObject(ii);
                                int numRegistrados = post.getInt("id");
                                if (numRegistrados > 0) {
                                    Filtro.setUsuario(post.getString("username"));
                                    Filtro.setRoles(post.getString("rol"));
                                    Filtro.setNivelRoles(post.getInt("NIVEL"));
                                    Log.i("Usuario",Filtro.getUsuario()+" "+Filtro.getRoles()+" "+Integer.toString(Filtro.getNivelroles()));
                                    result = 1; // Successful
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else{
                        result = 0; //"Failed to fetch data!";
                    }
                    if (result == 1) {
                        Toast.makeText(getBaseContext(), getPalabras("Usuario")+" "+getPalabras("Correcto"), Toast.LENGTH_SHORT).show();
                        // RELLENAMOS ACCIONES PERMITIDAS CRUGE
                        String url_cruge = Filtro.getUrl() + "/RellenaListaCruge.php";
                        lcruge = new ArrayList<Cruge>();
                        new GetCruge().execute(url_cruge);
                    } else {

                        Toast.makeText(getBaseContext(),getPalabras("Usuario")+" "+getPalabras("Incorrecto"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
                }

            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    public class GetUsuario extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected Integer doInBackground(String... params) {
            String cSql = "";
            String xWhere = "";
            xWhere = " WHERE usuarios.username='"+params[1]+"' AND usuarios.password='"+params[2]+"'";
            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Lista",cSql);
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

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);
                Log.i("md5: ", params[2]);

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

                    parseResultUsuario(response.toString());
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
            if (result == 1) {
                Toast.makeText(getBaseContext(), "Usuario correcto. ", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(LoginActivity.this, ActividadPrincipal.class);
                LoginActivity.this.startActivityForResult(myIntent, 0);
                finish();
            } else {
                Toast.makeText(getBaseContext(), "Usuario incorrecto. ", Toast.LENGTH_SHORT).show();
            }
        }
    }
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

    private void parseResultUsuario(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                int numRegistrados = post.getInt("id");
                if (numRegistrados > 0) {
                    Filtro.setUsuario(post.getString("username"));
                    Filtro.setRoles(post.getString("rol"));
                    Log.i("Usuario",Filtro.getUsuario()+" "+Filtro.getRoles());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Login Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    // usually, subclasses of AsyncTask are declared inside the activity class.
// that way, you can easily modify the UI thread from here
    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            mProgressDialog.dismiss();
            if (result != null) {
                Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/Download/com.ayansoft.androphp-1.2.0-www.APK4Fun.com.apk")), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // without this flag android returned a intent error!
                context.startActivity(intent);
            }
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                String PATH = Environment.getExternalStorageDirectory() + "/download/";
                String FILE = "com.ayansoft.androphp-1.2.0-www.APK4Fun.com.apk";
                output = new FileOutputStream(PATH + FILE);

                byte data[] = new byte[1024];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }
    }

    public boolean startFTP(String propertiesFile) {

        //       props = new Properties();

        try {

/*            props.load(new FileInputStream("properties/" + propertiesFile));

            String serverAddress = props.getProperty("serverAddress").trim();
            String userId = props.getProperty("userId").trim();
            String password = props.getProperty("password").trim();
            String remoteDirectory = props.getProperty("remoteDirectory").trim();
            String localDirectory = props.getProperty("localDirectory").trim();
*/
            String serverAddress = "ftp.seas-imagemaps.besaba.com";
            String userId = "u453462515";
            String password = "jcl8835amg";
            String remoteDirectory = "/public_html/android/tpv/download/scripts";
            String localDirectory = Environment.getExternalStorageDirectory() + "/www/tpv";
            //new ftp client
            FTPClient ftp = new FTPClient();
            //try to connect
            ftp.connect(serverAddress);
            //login to server
            if (!ftp.login(userId, password)) {
                ftp.logout();
                return false;
            }
            int reply = ftp.getReplyCode();
            //FTPReply stores a set of constants for FTP reply codes.
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return false;
            }

            //enter passive mode
            ftp.enterLocalPassiveMode();
            //get system name
            System.out.println("Remote system is " + ftp.getSystemType());
            sb_local.append('\n').append("Remote system is " + ftp.getSystemType());
            //change current directory
            ftp.changeWorkingDirectory(remoteDirectory);
            System.out.println("Current directory is " + ftp.printWorkingDirectory());
            sb_local.append('\n').append("Current directory is " + ftp.printWorkingDirectory());
            //get list of filenames
            FTPFile[] ftpFiles = ftp.listFiles();

            if (ftpFiles != null && ftpFiles.length > 0) {
                //loop thru files
                for (FTPFile file : ftpFiles) {
                    if (!file.isFile()) {
                        continue;
                    }
                    System.out.println("File is " + file.getName());
                    sb_local.append('\n').append("File is " + file.getName());


                    //get output stream
                    OutputStream output;
                    output = new FileOutputStream(localDirectory + "/" + file.getName());
                    //get the file from the remote system
                    ftp.retrieveFile(file.getName(), output);
                    //close output stream
                    output.close();

                    //delete the file
                    ///    ftp.deleteFile(file.getName());

                }
            }
            ftp.logout();
            ftp.disconnect();

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * Background Async Task to  Save Lecturas Details
     */
    class CreaDB extends AsyncTask<String, Void, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(LoginActivity.this);
            mProgressDialog.setMessage("Creando BASE DE DATOS ...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
        }

        /**
         * Saving Lectura
         */
        @Override
        protected Integer doInBackground(String... args) {
            int success = 0;

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();


            // sending modified data through http request
            // Notice that update Lectura url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_crea_db,
                    "POST", params);

            // check json success tag
            try {
                success = json.getInt(TAG_SUCCESS);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return success;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once Contador updated
            mProgressDialog.dismiss();
            if (success == 1) {
                // successfully updated
                sb_local.append('\n').append("Creacion Base de Datos - OK");
/*                Toast.makeText(
                        getBaseContext(),
                        "FINALIZADO CON EXITO", Toast.LENGTH_SHORT).show();
*/
                new CreaTablas().execute();
            } else {
                // failed to update Lectura
                sb_local.append('\n').append("Creacion Base de Datos - Not OK");
/*                Toast.makeText(
                        getBaseContext(),
                        "FINALIZADO CON ERRORES", Toast.LENGTH_SHORT).show();
*/
            }
        }
    }

    /**
     * Background Async Task to  Crear Estructura Tablas
     */
    class CreaTablas extends AsyncTask<String, Void, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(LoginActivity.this);
            mProgressDialog.setMessage("Creando Tablas ...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
        }

        /**
         * Saving Lectura
         */
        @Override
        protected Integer doInBackground(String... args) {
            int success = 0;

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();


            // sending modified data through http request
            // Notice that update Lectura url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_tablas,
                    "POST", params);

            // check json success tag
            try {
                success = json.getInt(TAG_SUCCESS);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return success;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once Contador updated
            mProgressDialog.dismiss();
            if (success == 1) {
                // successfully updated
                sb_local.append('\n').append("Creacion Tablas Base de Datos - OK");
/*                Toast.makeText(
                        getBaseContext(),
                        "FINALIZADO CON EXITO", Toast.LENGTH_SHORT).show();
*///                new UpdateTablas().execute();
            } else {
                sb_local.append('\n').append("Creacion Tablas Base de Datos - Not OK");

/*                Toast.makeText(
                        getBaseContext(),
                        "FINALIZADO CON ERRORES", Toast.LENGTH_SHORT).show();
*/
            }
        }
    }

    /**
     * Background Async Task to  Actualiza Tablas
     */
    class UpdateTablas extends AsyncTask<String, Void, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(LoginActivity.this);
            mProgressDialog.setMessage("Actualizando Tablas ...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
        }

        /**
         * Saving Lectura
         */
        @Override
        protected Integer doInBackground(String... args) {
            int success = 0;

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_FECHA, sFecha));


            // sending modified data through http request
            // Notice that update Lectura url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_update_tablas,
                    "POST", params);

            // check json success tag
            try {
                success = json.getInt(TAG_SUCCESS);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return success;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once Contador updated
            mProgressDialog.dismiss();
            if (success == 1) {
                // successfully updated
                Toast.makeText(
                        getBaseContext(),
                        "FINALIZADO CON EXITO", Toast.LENGTH_SHORT).show();
            } else {
                // failed to update Lectura
                Toast.makeText(
                        getBaseContext(),
                        "FINALIZADO CON ERRORES", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public static String getPalabras (String search){
//        Log.i("Palabra Valor: ",search+","+Integer.toString(lpalabras.size()));
        for(Palabras d : lpalabras){
//            Log.i("Palabra Clave: ",d.getPalabrasClavestring());
            if(d.getPalabrasClavestring().equals(search)){
//                Log.i("get1PalabrasId",d.getPalabrasPalabra());
                return d.getPalabrasPalabra();
            }
        }
        return "";
    }

    public String getNameResource(int id, Activity activity, String view) {
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
//        Log.i("get1TypeResource",view);
        String restext="";
        if (view.contains("TextView")){
            TextView text = (TextView) viewGroup.findViewById(id);
            restext = text.getText().toString();
//            Log.i("get1NameResource",text.getText().toString());
        }
        if (view.contains("Button")){
            Button text = (Button) viewGroup.findViewById(id);
            restext = text.getText().toString();
//            Log.i("get1NameResource",text.getText().toString());
        }
        return restext;
    }

    public String ValorCampo (int ID, String viewclass){
        String name = getNameResource(ID, LoginActivity.this,viewclass);
        if (!name.equals("")){
            String valorcampo = getPalabras(name);
            if(!valorcampo.equals("")){
                return valorcampo;
            }else{
                return name;
            }
        }
        return "**";
    }
    public class GetCruge extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogCruge = new ProgressDialog(LoginActivity.this);
            pDialogCruge.setMessage(getPalabras("Cargando")+" Cruge. "+getPalabras("Espere por favor")+"...");
            pDialogCruge.setIndeterminate(false);
            pDialogCruge.setCancelable(true);
            pDialogCruge.show();

        }

        @Override
        protected Integer doInBackground(String... params) {
            String cSql = "";
            String xWhere = "";
            if(!(Filtro.getUsuario().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE cruge_user.username='" + Filtro.getUsuario() + "'";
                } else {
                    xWhere += " AND cruge_user.username='" + Filtro.getUsuario() + "'";
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
                    for (Iterator<Cruge> it = lcruge.iterator(); it.hasNext();){
                        Cruge cruge = it.next();
                        it.remove();
                    }

                    parseResultcruge(response.toString());
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
            pDialogCruge.dismiss();
            if (result == 1) {
                Log.i(TAG_CRUGE, Integer.toString(lcruge.size()));
            } else {
                Log.e(TAG_CRUGE, "Failed to fetch data!");
            }
            Intent myIntent = new Intent(LoginActivity.this, ActividadPrincipal.class);
            LoginActivity.this.startActivityForResult(myIntent, 0);
            finish();

        }
    }
    private void parseResultcruge(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                Cruge cat = new Cruge(post.optString("ACTION"));
                Log.i(TAG_CRUGE,post.optString("ACTION"));
                lcruge.add(cat);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}

