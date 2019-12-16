package com.example.kcdwebservice.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpRestCall {

    public static String callGet(String strUrl, Map<String,String> param) {
        StringBuffer sb = new StringBuffer();
        HttpURLConnection conn = null;
        try {
            strUrl += ParameterStringBuilder.getParamsString(param);

            System.out.println("gun URL: " + strUrl);

            URL url = new URL(strUrl);

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;

            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                sb.append(output);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return sb.toString();

    }

    public static String callGet(String strUrl) {
        StringBuffer sb = new StringBuffer();
        HttpURLConnection conn = null;
        try {
            

            System.out.println("gun URL: " + strUrl);

            URL url = new URL(strUrl);

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;

            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                sb.append(output);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return sb.toString();

    }
}