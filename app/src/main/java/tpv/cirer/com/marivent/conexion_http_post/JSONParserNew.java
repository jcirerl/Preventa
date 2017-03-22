package tpv.cirer.com.marivent.conexion_http_post;

import android.content.ContentValues;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by JUAN on 15/09/2016.
 */
public class JSONParserNew {
    static HttpURLConnection urlConnection = null;
    static InputStream inputStream = null;
    static String json = "";
    static JSONObject jObj = null;

    // constructor
    public JSONParserNew() {

    }
    public JSONObject makeHttpRequest(String Url, String method, ContentValues values) {


        // Making HTTP request
        try {
            // forming th java.net.URL object
            URL url = new URL(Url);

            urlConnection = (HttpURLConnection) url.openConnection();

            // for Get request
            ///           urlConnection.setRequestMethod("GET");

            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod(method);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getQuery(values));
            writer.flush();
            writer.close();
            os.close();
            urlConnection.connect();
            int statusCode = urlConnection.getResponseCode();
            Log.i("STATUS CODE: ", method + " - " + Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
            // 200 represents HTTP OK
            if (statusCode == 200) {

                BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    response.append(line);
                }
                Log.i("JSON-->", response.toString());
                json = response.toString();

            } else {
                StringBuilder response = new StringBuilder();
                response.append(Integer.toString(statusCode));
                json = response.toString();
                Log.i("JSON-->", response.toString());
            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            jObj = new JSONObject(json);
            ///         JSONArray Jarray = jObj.getJSONArray("response");
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data [" + e.getMessage()+"] "+json);

        }
        return jObj;
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

}
