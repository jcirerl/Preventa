package tpv.cirer.com.marivent.ui;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.github.gcacace.signaturepad.views.SignaturePad;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.conexion_http_post.JSONParser;
import tpv.cirer.com.marivent.conexion_http_post.JSONParserNew;
import tpv.cirer.com.marivent.herramientas.Filtro;

import static tpv.cirer.com.marivent.ui.ActividadPrincipal.getLocalIpAddress;

//import static com.google.android.gms.internal.zzir.runOnUiThread;

/**
 * Created by JUAN on 11/11/2016.
 */

public class FirmaFragment extends Fragment {
    private static FirmaFragment Firma = null;
    private String pid;
    private String serie;
    private String factura;
    private String origen;

    // Progress Dialog
    private ProgressDialog pDialogftp;
    ProgressDialog dialog = null;
    private String SERVER_URL = Filtro.getUrl()+"/UploadToServer.php";
    private String selectedFilePath;

    // JSON parser class
    JSONParserNew jsonParserNew = new JSONParserNew();
    JSONParser jsonParser = new JSONParser();


    // url to update Factura
    private static final String url_update_Factura = Filtro.getUrl()+"/modifica_cobro_factura_imagen.php";

    // JSON Node names
    public static final String TAG = "Modifica Cobro";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PID = "pid";
    private static final String TAG_IMAGEN = "firma";
    private static final String TAG_TFRA = "t_fra";

    LinearLayout rootViewFirma;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private SignaturePad mSignaturePad;
    private Button mClearButton;
    private Button mSaveButton;
    private TextView lblAgreement;

    public static FirmaFragment newInstance(String id, String serie, String factura, String origen) {
        FirmaFragment Firma = new FirmaFragment();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("serie", serie);
        args.putString("factura", factura);
        args.putString("origen", origen);
        Firma.setArguments(args);
        return Firma;
    }

    public FirmaFragment() {
        // Required empty public constructor
    }
    public static FirmaFragment getInstance(){
        if(Firma == null){
            Firma = new FirmaFragment();
        }
        return Firma;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyStoragePermissions(getActivity());
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        pid = getArguments() != null ? getArguments().getString("id") : "0";
        serie = getArguments() != null ? getArguments().getString("serie") : "0";
        factura = getArguments() != null ? getArguments().getString("factura") : "0";
        origen = getArguments() != null ? getArguments().getString("origen") : "lista";
        ((ActividadPrincipal) getActivity()).setTitle("Firma Ticket "+pid);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //   View rootView = inflater.inflate(R.layout.add_lectura, container, false);
        rootViewFirma = (LinearLayout) inflater.inflate(R.layout.signature_main,container, false);
        mSignaturePad = (SignaturePad) rootViewFirma.findViewById(R.id.signature_pad);
        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                Toast.makeText(getActivity(), "OnStartSigning", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSigned() {
                mSaveButton.setEnabled(true);
                mClearButton.setEnabled(true);
            }

            @Override
            public void onClear() {
                mSaveButton.setEnabled(false);
                mClearButton.setEnabled(false);
            }
        });
        lblAgreement = (TextView) rootViewFirma.findViewById(R.id.signature_pad_description);
        lblAgreement.setText(ValorCampo(R.id.signature_pad_description, lblAgreement.getClass().getName(),0));

        mClearButton = (Button) rootViewFirma.findViewById(R.id.clear_button);
        mClearButton.setText(ValorCampo(R.id.clear_button, mClearButton.getClass().getName(),0));

        mSaveButton = (Button) rootViewFirma.findViewById(R.id.save_button);
        mSaveButton.setText(ValorCampo(R.id.save_button, mSaveButton.getClass().getName(),0));

        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignaturePad.clear();
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap signatureBitmap = mSignaturePad.getSignatureBitmap();
                if (addJpgSignatureToGallery(signatureBitmap)) {
                    Toast.makeText(getActivity(), "Signature saved into the Gallery", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Unable to store the signature", Toast.LENGTH_SHORT).show();
                }
 /*               if (addSvgSignatureToGallery(mSignaturePad.getSignatureSvg())) {
                    Toast.makeText(getActivity(), "SVG Signature saved into the Gallery", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Unable to store the SVG signature", Toast.LENGTH_SHORT).show();
                }
 */           }
        });


        return rootViewFirma;
    }
    public String getNameResource(int id, String view, int num) {
//        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        Log.i("get1TypeResource",view);
        String restext="";
        if (view.contains("TextView")){
            TextView text = (TextView) rootViewFirma.findViewById(id);
            restext = text.getText().toString();
            Log.i("get1NameResource",text.getText().toString());
        }
        if (view.contains("CheckBox")){
            CheckBox text = (CheckBox) rootViewFirma.findViewById(id);
            restext = text.getText().toString();
            Log.i("get1NameResource",text.getText().toString());
        }
        if (view.contains("Button")){
            Button text = (Button) rootViewFirma.findViewById(id);
            restext = text.getText().toString();
            Log.i("get1NameResource",text.getText().toString());
        }
        if (view.contains("ToggleButton")){
            if (num==0) {
                ToggleButton textOn = (ToggleButton) rootViewFirma.findViewById(id);
                restext = textOn.getText().toString();
                Log.i("get1NameResource",textOn.getText().toString());
            }else{
                ToggleButton textOff = (ToggleButton) rootViewFirma.findViewById(id);
                restext = textOff.getText().toString();
                Log.i("get1NameResource",textOff.getText().toString());
            }
        }
        return restext;
    }

    public String ValorCampo (int ID, String viewclass, int num ){
        String name = getNameResource(ID, viewclass, num);
        if (!name.equals("")){
            String valorcampo =((ActividadPrincipal)getActivity()).getPalabras(name);
            if(!valorcampo.equals("")){
                return valorcampo;
            }else{
                return name;
            }
        }
        return "**";
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length <= 0
                        || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "Cannot write images to external storage", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("SignaturePad", "Directory not created");
        }
        return file;
    }

    public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        stream.close();
    }

    public boolean addJpgSignatureToGallery(Bitmap signature) {
        boolean result = false;
        try {
            String keysignature = Filtro.getGrupo().trim()+
                                  Filtro.getEmpresa().trim()+
                                  Filtro.getLocal().trim()+
                                  Filtro.getSeccion().trim()+
                                  Filtro.getCaja().trim()+
                                  serie.trim()+
                                  factura.trim();

///            File photo = new File(getAlbumStorageDir("SignaturePad"), String.format("Signature_%d.jpg", System.currentTimeMillis()));
            File photo = new File(getAlbumStorageDir("SignaturePad"), String.format("Signature_%s.jpg", keysignature ));
            saveBitmapToJPG(signature, photo);
            scanMediaFile(photo);
            result = true;
            Log.i("URLPHOTO1",photo.getAbsolutePath()+" "+Filtro.getUrl()+"/firmas/"+pid+".jpg");
            //on upload button Click
            selectedFilePath = photo.getAbsolutePath();
            if(selectedFilePath != null){
                dialog = ProgressDialog.show(getActivity(),"","Uploading File...",true);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //creating new thread to handle Http Operations
                        uploadFile(selectedFilePath);
                    }
                }).start();
            }else{
                Toast.makeText(getActivity(),"Please choose a File First",Toast.LENGTH_SHORT).show();
            }


///            copyFile(photo.getAbsolutePath(),"http://192.168.1.35:8080"+"/firmas/"+pid+".jpg");
/*            URL website = new URL(photo.getAbsolutePath());
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(Filtro.getUrl()+"/firmas/"+pid+".jpg");
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
*/

            // INICIAMOS GRABACION EN BASE DE DATOS
//            new SaveFirmaCobroFactura().execute(photo.getAbsolutePath());
//            new ModificaImagen().execute(photo.getAbsolutePath());

        } catch (IOException e) {
            Log.i("URLPHOTO1","ERROR");
            e.printStackTrace();
        }
        return result;
    }

    private void scanMediaFile(File photo) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }

    public boolean addSvgSignatureToGallery(String signatureSvg) {
        boolean result = false;
        try {
            File svgFile = new File(getAlbumStorageDir("SignaturePad"), String.format("Signature_%d.svg", System.currentTimeMillis()));
            OutputStream stream = new FileOutputStream(svgFile);
            OutputStreamWriter writer = new OutputStreamWriter(stream);
            writer.write(signatureSvg);
            writer.close();
            stream.flush();
            stream.close();
            scanMediaFile(svgFile);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Checks if the app has permission to write to device storage
     * <p/>
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity the activity from which permissions are checked
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
    public int uploadFile(final String selectedFilePath){

        int serverResponseCode = 0;

        HttpURLConnection connection;
        DataOutputStream dataOutputStream;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";


        int bytesRead,bytesAvailable,bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File selectedFile = new File(selectedFilePath);


        String[] parts = selectedFilePath.split("/");
        final String fileName = parts[parts.length-1];

        if (!selectedFile.isFile()){
            dialog.dismiss();

            ((ActividadPrincipal) getActivity()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), "Source File Doesn't Exist: " + selectedFilePath, Toast.LENGTH_SHORT).show();
//                    tvFileName.setText("Source File Doesn't Exist: " + selectedFilePath);
                }
            });
            return 0;
        }else{
            try{
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                URL url = new URL(SERVER_URL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);//Allow Inputs
                connection.setDoOutput(true);//Allow Outputs
                connection.setUseCaches(false);//Don't use a cached Copy
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                connection.setRequestProperty("uploaded_file",selectedFilePath);

                //creating new dataoutputstream
                dataOutputStream = new DataOutputStream(connection.getOutputStream());

                //writing bytes to data outputstream
                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + selectedFilePath + "\"" + lineEnd);

                dataOutputStream.writeBytes(lineEnd);

                //returns no. of bytes present in fileInputStream
                bytesAvailable = fileInputStream.available();
                //selecting the buffer size as minimum of available bytes or 1 MB
                bufferSize = Math.min(bytesAvailable,maxBufferSize);
                //setting the buffer as byte array of size of bufferSize
                buffer = new byte[bufferSize];

                //reads bytes from FileInputStream(from 0th index of buffer to buffersize)
                bytesRead = fileInputStream.read(buffer,0,bufferSize);

                //loop repeats till bytesRead = -1, i.e., no bytes are left to read
                while (bytesRead > 0){
                    //write the bytes read from inputstream
                    dataOutputStream.write(buffer,0,bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable,maxBufferSize);
                    bytesRead = fileInputStream.read(buffer,0,bufferSize);
                }

                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                serverResponseCode = connection.getResponseCode();
                String serverResponseMessage = connection.getResponseMessage();

                Log.i(TAG, "Server Response is: " + serverResponseMessage + ": " + serverResponseCode);

                //response code of 200 indicates the server status OK
                if(serverResponseCode == 200){
                    ((ActividadPrincipal) getActivity()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
     //                       tvFileName.setText("File Upload completed.\n\n You can see the uploaded file here: \n\n" + "http://coderefer.com/extras/uploads/"+ fileName);
                            Toast.makeText(getActivity(),"File Upload completed.\n" +
                                    "\n" +
                                    " You can see the uploaded file here: \n" +
                                    "\n" +Filtro.getUrl()+"/firmas/"+ fileName,Toast.LENGTH_SHORT).show();
                            new SaveFirmaCobroFactura().execute(Filtro.getIp_url()+"/firmas/"+fileName);
                            Filtro.setUrlFirma(Filtro.getIp_url()+"/firmas/"+fileName);
                        }
                    });


                 }

                //closing the input and output streams
                fileInputStream.close();
                dataOutputStream.flush();
                dataOutputStream.close();



            } catch (FileNotFoundException e) {
                e.printStackTrace();
                ((ActividadPrincipal) getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),"File Not Found",Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "URL error!", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Cannot Read/Write File!", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
            return serverResponseCode;
        }

    }

    public static boolean copyFile(String from, String to) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            if (sd.canWrite()) {
                int end = from.toString().lastIndexOf("/");
                String str1 = from.toString().substring(0, end);
                String str2 = from.toString().substring(end+1, from.length());
                File source = new File(str1, str2);
                File destination= new File(to, str2);
                if (source.exists()) {
                    FileChannel src = new FileInputStream(source).getChannel();
                    FileChannel dst = new FileOutputStream(destination).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * Background Async Task to  Save Factura Details
     * */
    class SaveFirmaCobroFactura extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
/*            super.onPreExecute();
            pDialogftp = new ProgressDialog(getActivity());
            pDialogftp.setMessage("Guardando Firma ...");
            pDialogftp.setIndeterminate(false);
            pDialogftp.setCancelable(true);
            pDialogftp.show();
*/        }

        /**
         * Saving Factura
         * */
        @Override
        protected Integer doInBackground(String... args) {
            // getting updated data from EditTexts
            Integer result = 0;

            Calendar currentDate = Calendar.getInstance(); //Get the current date
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
            String dateNow = formatter.format(currentDate.getTime());

            ContentValues values = new ContentValues();
            values.put(TAG_PID, pid);
            values.put(TAG_TFRA,Filtro.getT_fra());
            values.put(TAG_IMAGEN,args[0]);
            values.put("updated", dateNow);
            values.put("usuario", Filtro.getUsuario());
            values.put("ip",getLocalIpAddress());

            // sending modified data through http request
            // Notice that update Factura url accepts POST method
            JSONObject json = jsonParserNew.makeHttpRequest(url_update_Factura,
                    "POST", values);

            // check json success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    result = 1;
                } else {
                    result = 0;
                    // failed to update Factura
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer result) {
            // dismiss the dialog once Factura updated
            if (result==1) {
                if (origen.equals("lista")) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
                if (origen.equals("factura")) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
            //          pDialogftp.dismiss();
        }
    }
     /**
     * Background Async Task to Create new product
     * */

    class ModificaImagen extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogftp = new ProgressDialog(getActivity());
            pDialogftp.setMessage("Modifica Imagen..");
            pDialogftp.setIndeterminate(false);
            pDialogftp.setCancelable(true);
            pDialogftp.show();
        }

        /**
         * Update imagen
         * */
        @Override
        protected Integer doInBackground(String... args) {
            Integer result = 0;
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_PID, pid));

            // getting FIRMA GRABADA Y CONVERTIRLA PARA ALMACENAR EN BBDD
            try {
//                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.logo_ricoparico);
                Bitmap bitmap = BitmapFactory.decodeFile(args[0]);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream); //compress to which format you want.
                byte [] byte_arr = stream.toByteArray();
                String image_str = Base64.encodeToString(byte_arr, Base64.DEFAULT);

                Log.i("URLPHOTO",args[0]+" "+image_str);
                params.add(new BasicNameValuePair(TAG_IMAGEN, args[0]));

            } catch (Exception e) {
                Log.i("URLPHOTO","ERROR");
                e.printStackTrace();
            }




            Calendar currentDate = Calendar.getInstance(); //Get the current date
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
            String dateNow = formatter.format(currentDate.getTime());
            // Building Parameters
             params.add(new BasicNameValuePair("updated", dateNow));
            params.add(new BasicNameValuePair("usuario", Filtro.getUsuario()));
            params.add(new BasicNameValuePair("ip",getLocalIpAddress()));


            // sending modified data through http request
            // Notice that update Factura url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_update_Factura,
                    "POST", params);

            // check json success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    result = 1;
                    if (origen.equals("lista")) {
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                } else {
                    result = 0;
                    // failed to update Factura
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;

        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer result) {
            // dismiss the dialog once done
            pDialogftp.dismiss();
        }

    }


}
