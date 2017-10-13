package tpv.cirer.com.restaurante.servicios;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import tpv.cirer.com.restaurante.herramientas.Filtro;

public class ServiceMesas extends Service {
	String URL_MESSAGE;
	String TAG_MESSAGE = "MESSAGE ";
    int countMessage;
	Timer timer = new Timer();
	MyTimerTask timerTask;
	ResultReceiver resultReceiver;

	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

        URL_MESSAGE=Filtro.getUrl()+"/CountMessageOpen.php";
		resultReceiver = intent.getParcelableExtra("receiver");
		
		timerTask = new MyTimerTask();
		timer.scheduleAtFixedRate(timerTask, Filtro.getOpintervalo(), Filtro.getOpintervalo());
		return START_STICKY;
	}
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		timer.cancel();
		Bundle bundle = new Bundle();
		bundle.putString("end", "Timer Stopped....");
		resultReceiver.send(200, bundle);
	}
	
	class MyTimerTask extends TimerTask
	{
		public MyTimerTask() {
			Bundle bundle = new Bundle();
			bundle.putString("start", "Timer Started....");
			resultReceiver.send(100, bundle);
		}
		@Override
		public void run() {
            countMessage = 0;
			new GetMessage().execute(URL_MESSAGE);
////			SimpleDateFormat dateFormat = new SimpleDateFormat("s");
////			resultReceiver.send(Integer.parseInt(dateFormat.format(System.currentTimeMillis())), null);
		}
	}
	public class GetMessage extends AsyncTask<String, Void, Integer> {

		@Override
		protected void onPreExecute() {
			//setProgressBarIndeterminateVisibility(true);
		}

		@Override
		protected Integer doInBackground(String... params) {
//            Integer result = 0;
			String cSql = "";
			String xWhere = "";
            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE message.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND message.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE message.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND message.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE message.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND message.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE message.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND message.SECCION='" + Filtro.getSeccion() + "'";
                }
            }
            if(!(Filtro.getCaja().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE message.CAJA='" + Filtro.getCaja() + "'";
                } else {
                    xWhere += " AND message.CAJA='" + Filtro.getCaja() + "'";
                }
            }
            if(!(Filtro.getFechaapertura().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE DATE(message.creado)='" + Filtro.getFechaapertura() + "'";
                } else {
                    xWhere += " AND DATE(message.creado)='" + Filtro.getFechaapertura() + "'";
                }
            }

            xWhere += " AND message.ACTIVO=1";

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
						result = 0;
					}else {
						parseResultMessage(response.toString());
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
			if (result == 1) {
				Log.e(TAG_MESSAGE, "OK MESSAGE");
			} else {
				Log.e(TAG_MESSAGE, "Failed to fetch data!");
			}
            resultReceiver.send(countMessage, null);

		}
	}
	private void parseResultMessage(String result) {
		try {
			JSONObject response = new JSONObject(result);
			JSONArray posts = response.optJSONArray("posts");

			Log.i("Longitud Datos: ",Integer.toString(posts.length()));
			for (int ii = 0; ii < posts.length(); ii++) {
				JSONObject post = posts.optJSONObject(ii);
				countMessage = post.optInt("COUNT");
			}
		} catch (JSONException e) {
			e.printStackTrace();
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


}
