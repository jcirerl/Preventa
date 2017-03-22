package tpv.cirer.com.marivent.conexion_http_post;


import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Post {
	private InputStream is = null;
	private String respuesta="";
	private void conectaPost(ArrayList<String> parametros,String URL)throws Exception{
		ArrayList<NameValuePair> nameValuePairs = null;
		HttpClient httpclient = null;
		HttpPost httppost = null;
		HttpResponse response = null;
		HttpEntity entity = null;
		try{
              httpclient = new DefaultHttpClient();
      	      httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY,
    		        CookiePolicy.BROWSER_COMPATIBILITY);
			  httppost = new HttpPost(URL);
		      Log.i("url post", URL);
			  nameValuePairs = new ArrayList<NameValuePair>();
			  
			  if(parametros!=null){
				  for(int i = 0 ;i< parametros.size()-1;i+=2){
					  nameValuePairs.add(new BasicNameValuePair(parametros.get(i), parametros.get(i+1)));  
				  }
				  httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			  }
			  response = httpclient.execute(httppost);
			  entity = response.getEntity();
              is = entity.getContent();
		}catch(Exception e){
				Log.e("log_tag", "Error in http connection "+e.toString());
				throw new Exception("Error al conectar con el servidor. ");
		}finally{
			if(entity!=null){entity=null;};
			if(response!=null){response=null;};
			if(httppost!=null){httppost=null;};
			if(httpclient!=null){httpclient=null;};
		}
	}
	private void getRespuestaPost()throws Exception{
		BufferedReader reader = null;
		try{
				reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                	sb.append(line + "\n");
                }
                is.close();
                respuesta=sb.toString();
                Log.e("log_tag", "Cadena JSon "+ respuesta);
        }catch(Exception e){
                Log.e("log_tag", "Error converting result "+e.toString());
                throw new Exception("Error al recuperar las imagenes del servidor. ");
        }finally{
			if(reader!=null){reader.close();};
		}
	}
	@SuppressWarnings("finally")
	private JSONArray getJsonArray()throws Exception{
		JSONArray jArray = null;
		try{
		    jArray = new JSONArray(respuesta);
		}catch(Exception e){
			throw new Exception("Error al convertir a JSonArray. ");
		}finally{
			return jArray;
		}
	}
	public JSONArray getServerData(ArrayList<String> parametros, String URL) throws Exception{
		JSONArray jsonArray = null;
		try{
			conectaPost(parametros, URL);
	        getRespuestaPost();
	        jsonArray = getJsonArray();
	        return jsonArray;
		}catch(Exception e){
	    	throw new Exception(e.getMessage());
	     }
	}	
}
