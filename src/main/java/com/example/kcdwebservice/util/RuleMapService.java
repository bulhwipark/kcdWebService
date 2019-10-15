package com.example.kcdwebservice.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

public class RuleMapService {

    // http://localhost:8080/RESTfulExample/json/product/get
    public static void main(String[] args) throws JSONException {

	  try {
        
        String strUrl= "http://1.224.169.78:8095/MAIN/concepts?";
        Map<String,String> hm = new HashMap<String,String>();

        hm.put("activeFilter", "true");
        hm.put("termActive", "true");
        hm.put("statedEcl", "<64572001");
        hm.put("term", "heart attack");

        strUrl +=ParameterStringBuilder.getParamsString(hm);

        System.out.println("URL: "+ strUrl);

        URL url = new URL(strUrl);

        
       
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));

        String output;
        StringBuffer sb =new StringBuffer();
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
            System.out.println(output);
            sb.append(output);
        }
        
        JSONObject jobj = new JSONObject(sb.toString());


        System.out.println("out:"+jobj);
        
        JSONArray ja = jobj.getJSONArray("items");

        for(int x=0; x<ja.length();x++){
            JSONObject jo = ja.getJSONObject(x);
            System.out.println("idx:"+x+" body: "+ jo.toString() );

        }

		conn.disconnect();

	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	  }

	}

}