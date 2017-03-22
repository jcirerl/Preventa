package tpv.cirer.com.marivent.ui;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.conexion_http_post.JSONParser;
import tpv.cirer.com.marivent.herramientas.Filtro;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JUAN on 13/09/2016.
 */
public class FragmentoPendiente extends Fragment implements Runnable {
    private ProgressDialog pd;
    private String contador;
    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    // url to update Contador
    private static final String url_update_lecturas = Filtro.getUrl()+"/lect_slave_server.php";


    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_EMPRESA = "empresa";

    Button btsave;

    public FragmentoPendiente() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout rootView = (LinearLayout) inflater.inflate(R.layout.open_seccion,container, false);
        Button btsave = (Button) rootView.findViewById(R.id.btnopenseccion);
        //	btsave.setVisibility(4);
        btsave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (isOnline()){
                    if (Filtro.getConexion().equals("LOCAL")) {
                        new SaveLecturaDetails().execute();
                    }else{
                        Toast.makeText(
                                getActivity(),
                                "OPCION SOLO SE PUEDE HACER EN CONEXION LOCAL", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(
                            getActivity(),
                            "NO HAY CONEXION INTERNET", Toast.LENGTH_SHORT).show();


//				update_contador();
                }
            }

        });


        return rootView;

 //       return inflater.inflate(R.layout.fragmento_tarjetas, container, false);
    }
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        return false;
    }

    /**
     * Background Async Task to  Save Lecturas Details
     * */
    class SaveLecturaDetails extends AsyncTask<String, Void, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd.setMessage("Guardando Lecturas ...");
            pd.setIndeterminate(false);
            pd.setCancelable(true);
            pd.show();
        }

        /**
         * Saving Lectura
         * */
        @Override
        protected Integer doInBackground(String... args) {
            int success = 0;
            // getting updated data from EditTexts
            String empresa = Filtro.getEmpresa();

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_EMPRESA, empresa));

            ContentValues values = new ContentValues();
            values.put("TAG_EMPRESA", empresa);

            // sending modified data through http request
            // Notice that update Lectura url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_update_lecturas,
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
         * **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once Contador updated
            pd.dismiss();
            if (success == 1) {
                // successfully updated
                Toast.makeText(
                        getActivity(),
                        "FINALIZADO CON EXITO", Toast.LENGTH_SHORT).show();
                //                   getActivity().getSupportFragmentManager().popBackStack();
            } else {
                // failed to update Lectura
                Toast.makeText(
                        getActivity(),
                        "FINALIZADO CON ERRORES", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

    }


}
